package com.demo.ecommerce.service.product;

import com.demo.ecommerce.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
