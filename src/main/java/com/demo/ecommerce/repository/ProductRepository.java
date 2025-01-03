package com.demo.ecommerce.repository;

import com.demo.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
//    List<Product> findByCategoryName(String category);
//
//    List<Product> findByBrand(String brand);
//
//    List<Product> findByName(String name);
//
//    List<Product> findByCategoryNameAndBrand(String category, String brand);
//
//    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);
}
