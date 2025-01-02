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

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(new ApiResponse("Success", savedProduct));
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }


    @GetMapping("/product/{id}/select")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("Success", product));
    }


    @GetMapping("/product/{name}/name")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }


    @GetMapping("/product/{category}/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }


    @GetMapping("/product/{brand}/brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }


    @GetMapping("/product/{category}/{brand}/category&brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {
        List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }


    @GetMapping("/product/{brand}/{name}/brand&name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {
        List<Product> products = productService.getProductsByBrandAndName(brand, name);
        return ResponseEntity.ok(new ApiResponse("Success", products));
    }


    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest requestProduct) {
        Product updatedProduct = productService.updateProduct(requestProduct, id);
        return ResponseEntity.ok(new ApiResponse("Update success", updatedProduct));
    }


    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(new ApiResponse("Delete success", null));
    }

}
