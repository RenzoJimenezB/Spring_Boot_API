package com.demo.ecommerce.controller;

import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.response.ApiResponse;
import com.demo.ecommerce.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(new ApiResponse("Success", savedCategory));
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Success", categories));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Success", category));
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name)
                .map(category -> ResponseEntity.ok(new ApiResponse("Success", category)))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Category not found", null)));
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
