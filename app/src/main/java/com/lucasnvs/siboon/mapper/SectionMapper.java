package com.lucasnvs.siboon.mapper;

import com.lucasnvs.siboon.data.source.remote.NetworkSection;
import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.model.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionMapper implements IMapper<Section, NetworkSection> {

    @Override
    public Section toModel(NetworkSection item) {
        return new Section(
                item.getName(),
                new ArrayList<>()
        );
    }

    public Section toModel(NetworkSection item, List<Product> products) {
        return new Section(
                item.getName(),
                products
        );
    }

    @Override
    public List<Section> toModel(List<NetworkSection> items) {
        return null;
    }

    @Override
    public NetworkSection toNetwork(Section item) {
        return null;
    }

    @Override
    public List<NetworkSection> toNetwork(List<Section> items) {
        return null;
    }
}
