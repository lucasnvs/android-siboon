package com.lucasnvs.siboon.mapper;

import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.data.source.remote.NetworkProduct;

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
                networkProduct.getPrincipalImg(),
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

    @Override
    public NetworkProduct toNetwork(Product item) {
        return null;
    }

    @Override
    public List<NetworkProduct> toNetwork(List<Product> items) {
        return Collections.emptyList();
    }
}
