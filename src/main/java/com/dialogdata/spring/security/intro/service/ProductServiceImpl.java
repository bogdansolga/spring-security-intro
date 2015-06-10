package com.dialogdata.spring.security.intro.service;

import com.dialogdata.spring.security.intro.data.dao.ProductRepository;
import com.dialogdata.spring.security.intro.data.entities.Product;
import com.dialogdata.spring.security.intro.transport.ProductTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductTO> getAll() {
        final List<ProductTO> productTOs = new LinkedList<>();

        final List<Product> products = productRepository.findAll();
        productTOs.addAll(products.stream().map(this::buildProductTO).collect(Collectors.toList()));

        return productTOs;
    }

    @Override
    public ProductTO get(final Integer id) {
        LOGGER.info("Retrieving the product with the ID '{}'...", id);

        Product product = productRepository.findOne(id);

        return buildProductTO(product);
    }

    private ProductTO buildProductTO(final Product product) {
        final ProductTO productTO = new ProductTO();

        productTO.setId(product.getId());
        productTO.setName(product.getName());
        productTO.setDescription(product.getDescription());

        return productTO;
    }
}
