package com.demo.ecommerce.controller;

import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.request.AddProductRequest;
import com.demo.ecommerce.request.ProductUpdateRequest;
import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;


    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(new ApiResponse("Success", savedProduct));
    }


    @GetMapping
    public ResponseEntity<ApiResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "false") boolean count) {

        if (count) {
            Long productCount = productService.countProducts(name, brand, category);
            return buildResponse(productCount == 0, productCount);

        } else {
            List<Product> products = productService.searchProducts(name, brand, category);
            return buildResponse(products.isEmpty(), products);
        }
    }


    private <T> ResponseEntity<ApiResponse> buildResponse(boolean isEmpty, T data) {
        if (isEmpty)
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Products not found", null));

        return ResponseEntity.ok(new ApiResponse("Success", data));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Success", product));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest requestProduct) {
        Product updatedProduct = productService.updateProduct(requestProduct, id);
        return ResponseEntity.ok(new ApiResponse("Update success", updatedProduct));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(new ApiResponse("Delete success", null));
    }
}
