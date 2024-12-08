package com.lucasnvs.siboon.data.repository;

import android.content.Context;

import com.lucasnvs.siboon.data.source.remote.NetworkSection;
import com.lucasnvs.siboon.data.source.remote.RetrofitClient;
import com.lucasnvs.siboon.data.source.remote.SiboonApi;
import com.lucasnvs.siboon.mapper.SectionMapper;
import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.model.Section;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SectionRepository {
    private final SiboonApi api;
    private final SectionMapper sectionMapper;
    private final ProductRepository productRepository;

    public SectionRepository(Context context) {
        this.api = RetrofitClient.getApi(context);
        this.sectionMapper = new SectionMapper();
        this.productRepository = new ProductRepository(context);
    }

    public Single<List<Section>> fetchSectionsWithProducts() {
        return api.getSections()
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    if (response == null || response.getData() == null) {
                        return Single.error(new NullPointerException("Resposta ou dados da seção estão nulos"));
                    }

                    List<Single<Section>> sectionSingles = new ArrayList<>();
                    for (NetworkSection networkSection : response.getData()) {
                        if (networkSection != null) {
                            Single<List<Product>> productsSingle = fetchProductsForSection(networkSection);
                            sectionSingles.add(productsSingle.map(products -> sectionMapper.toModel(networkSection, products)));
                        }
                    }

                    return Single.zip(sectionSingles, args -> {
                        List<Section> sections = new ArrayList<>();
                        for (Object obj : args) {
                            if (obj instanceof Section) {
                                sections.add((Section) obj);
                            }
                        }
                        return sections;
                    }).onErrorReturn(throwable -> new ArrayList<>());
                }).onErrorReturn(throwable -> new ArrayList<>());
    }

    private Single<List<Product>> fetchProductsForSection(NetworkSection section) {
        if (section == null || section.getFeaturedItems() == null) {
            return Single.error(new NullPointerException("Seção ou itens em destaque estão nulos"));
        }

        List<Single<Product>> productSingles = new ArrayList<>();
        for (NetworkSection.FeaturedItemResponse featuredItem : section.getFeaturedItems()) {
            if (featuredItem != null) {
                Single<Product> productSingle = productRepository.fetchProductById(featuredItem.getProductId());
                productSingles.add(productSingle);
            }
        }
        return Single.zip(productSingles, args -> {
            List<Product> products = new ArrayList<>();
            for (Object obj : args) {
                if (obj instanceof Product) {
                    products.add((Product) obj);
                }
            }
            return products;
        }).onErrorReturn(throwable -> new ArrayList<>());
    }
}