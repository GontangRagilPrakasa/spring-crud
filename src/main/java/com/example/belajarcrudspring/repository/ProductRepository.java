package com.example.belajarcrudspring.repository;

import com.example.belajarcrudspring.domain.dao.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductDao, Long> {
    List<ProductDao> findByProductName(String product_name);
}
