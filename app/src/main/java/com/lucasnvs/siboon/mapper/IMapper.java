package com.lucasnvs.siboon.mapper;

import java.util.List;


public interface IMapper<X, Y> {

    public X toModel(Y item);
    public List<X> toModel(List<Y> items);

    public Y toNetwork(X item);
    public List<Y> toNetwork(List<X> items);

}
