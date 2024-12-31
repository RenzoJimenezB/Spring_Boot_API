package com.demo.ecommerce.service.category;

import com.demo.ecommerce.exception.AlreadyExistsException;
import com.demo.ecommerce.exception.ResourceNotFoundException;
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

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName()))
            throw new AlreadyExistsException(category.getName() + " already exists");

        return categoryRepository.save(category);

//        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
//                .map(categoryRepository::save)
//                .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category updateCategory(Category requestCategory, Long categoryId) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(requestCategory.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);

//        categoryRepository.findById(id)
//                .ifPresentOrElse(categoryRepository::delete, () -> {
//                    throw new ResourceNotFoundException("Category not found");
//                });
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
