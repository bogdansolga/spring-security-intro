package com.dialogdata.spring.security.intro.data.dao;

import com.dialogdata.spring.security.intro.data.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}