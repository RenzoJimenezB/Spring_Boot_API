package com.demo.ecommerce.service.category;

import com.demo.ecommerce.dto.category.CategoryResponse;
import com.demo.ecommerce.exception.AlreadyExistsException;
import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.mapper.CategoryMapper;
import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {


    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName()))
            throw new AlreadyExistsException(category.getName() + " already exists");

        return categoryRepository.save(category);
    }


    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }


    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }


    @Override
    public CategoryResponse getCategoryResponseById(Long id) {
        Category category = getCategoryEntityById(id);
        return categoryMapper.toCategoryResponse(category);
    }


    private Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }


    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }


    @Override
    public Category updateCategory(Category requestCategory, Long categoryId) {
        Category existingCategory = getCategoryEntityById(categoryId);
        existingCategory.setName(requestCategory.getName());
        return categoryRepository.save(existingCategory);
    }


    @Override
    public void deleteCategoryById(Long id) {
        Category category = getCategoryEntityById(id);
        categoryRepository.delete(category);
    }
}
