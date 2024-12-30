package com.demo.ecommerce.service.product;

import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.request.AddProductRequest;
import com.demo.ecommerce.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    void deleteProduct(Long id);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
