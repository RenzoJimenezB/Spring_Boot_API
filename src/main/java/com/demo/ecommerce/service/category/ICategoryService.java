package com.demo.ecommerce.service.category;

import com.demo.ecommerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category updateCategory(Category category, Long categoryId);

    void deleteCategoryById(Long id);

    Optional<Category> getCategoryByName(String name);


}
