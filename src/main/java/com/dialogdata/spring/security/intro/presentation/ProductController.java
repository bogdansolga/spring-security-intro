package com.dialogdata.spring.security.intro.presentation;

import com.dialogdata.spring.security.intro.service.ProductService;
import com.dialogdata.spring.security.intro.transport.ProductTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping
    @PreAuthorize("authenticated AND hasRole('ROLE_MANAGER')")
    public Collection<ProductTO> getAll() {
        return productService.getAll();
    }

    @RequestMapping(value = "/{id}")
    public @ResponseBody ProductTO get(@PathVariable Integer id) {
        return productService.get(id);
    }
}
