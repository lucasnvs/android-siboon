package com.lucasnvs.siboon.mapper;

import java.util.List;


public interface IMapper<X, Y> {

    X toModel(Y item);
    List<X> toModel(List<Y> items);

    Y toNetwork(X item);
    List<Y> toNetwork(List<X> items);

}
