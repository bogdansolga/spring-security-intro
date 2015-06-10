package com.dialogdata.spring.security.intro.service;

import com.dialogdata.spring.security.intro.transport.ProductTO;

import java.util.List;

public interface ProductService {
    List<ProductTO> getAll();

    ProductTO get(Integer id);
}
