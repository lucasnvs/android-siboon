package com.lucasnvs.siboon.mapper;

import com.lucasnvs.siboon.data.source.local.LocalProduct;
import com.lucasnvs.siboon.data.source.remote.NetworkProduct;
import com.lucasnvs.siboon.model.Product;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductMapper implements IMapper<Product, NetworkProduct>{

    public Product toModel(NetworkProduct networkProduct) {
        if (networkProduct == null) {
            return null;
        }

        return new Product(
                networkProduct.getId(),
                networkProduct.getName(),
                networkProduct.getPriceBrl() != null ? networkProduct.getPriceBrl() : 0.0,
                networkProduct.getPrincipalImage(),
                networkProduct.getAdditionalImages(),
                networkProduct.getDescription(),
                networkProduct.getMaxInstallments()
        );
    }

    public List<Product> toModel(List<NetworkProduct> networkProductList) {
        if (networkProductList == null || networkProductList.isEmpty()) {
            return Collections.emptyList();
        }

        return networkProductList.stream()
                .map(this::toModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Product toModel(LocalProduct localProduct) {
        if (localProduct == null) {
            return null;
        }

        return new Product(
                localProduct.getId(),
                localProduct.getName(),
                localProduct.getPriceBrl() != null ? localProduct.getPriceBrl() : 0.0,
                localProduct.getPrincipalImage(),
                localProduct.getAdditionalImages(),
                localProduct.getDescription(),
                localProduct.getMaxInstallments()
        );
    }

    @Override
    public NetworkProduct toNetwork(Product item) {
        return null;
    }

    @Override
    public List<NetworkProduct> toNetwork(List<Product> items) {
        return Collections.emptyList();
    }

    public LocalProduct toLocal(Product product) {
        if (product == null) {
            return null;
        }

        return new LocalProduct(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                null,
                product.getPrice() != null ? product.getPrice() : 0.0,
                0,
                product.getInstallments(),
                0,
                product.getImageSrc(),
                product.getAdditionalImagesSrc()
        );
    }

    public List<LocalProduct> toLocal(List<Product> productList) {
        if (productList == null || productList.isEmpty()) {
            return Collections.emptyList();
        }

        return productList.stream()
                .map(this::toLocal)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
