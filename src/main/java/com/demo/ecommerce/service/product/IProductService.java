package com.demo.ecommerce.service.product;

import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.request.AddProductRequest;
import com.demo.ecommerce.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    List<Product> searchProducts(String name, String brand, String category);

    Long countProducts(String brand, String name, String category);

    Product getProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    void deleteProductById(Long id);
}
