package com.demo.ecommerce.controller;

import com.demo.ecommerce.dto.response.CategoryResponse;
import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.dto.response.ApiResponse;
import com.demo.ecommerce.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {


    private final ICategoryService categoryService;


    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.addCategory(category);
        URI location = URI.create("/categories/" + savedCategory.getId());

        return ResponseEntity.created(location)
                .body(new ApiResponse("Success", savedCategory));
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Success", categories));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryResponseById(id);
        return ResponseEntity.ok(new ApiResponse("Success", category));
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name)
                .map(category -> ResponseEntity.ok(new ApiResponse("Success", category)))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category requestCategory) {
        Category updatedCategory = categoryService.updateCategory(requestCategory, id);
        return ResponseEntity.ok(new ApiResponse("Update success", updatedCategory));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Delete success", null));
    }

}
