package com.demo.ecommerce.service.product;

import com.demo.ecommerce.dto.product.ProductResponse;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.dto.product.AddProductRequest;
import com.demo.ecommerce.dto.product.ProductUpdateRequest;
import org.springframework.data.domain.Page;


public interface IProductService {
    Product addProduct(AddProductRequest product);

    Page<ProductResponse> searchProducts(String name, String brand, String category, int page, int size);

    Long countProducts(String name, String brand, String category);

    Product getProductEntityById(Long productId);

    ProductResponse getProductResponseById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    void deleteProductById(Long id);
}
