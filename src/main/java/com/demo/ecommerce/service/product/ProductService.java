package com.demo.ecommerce.service.product;

import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.ProductRepository;
import com.demo.ecommerce.repository.specification.ProductSpecifications;
import com.demo.ecommerce.request.AddProductRequest;
import com.demo.ecommerce.request.ProductUpdateRequest;
import com.demo.ecommerce.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ICategoryService categoryService;

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = categoryService.getCategoryByName(request.getCategory().getName())
                .orElseGet(() -> categoryService.createCategory(request.getCategory()));
        request.setCategory(category);

        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getStock(),
                request.getDescription(),
                category
        );
    }

//    @Override
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        Product existingProduct = getProductById(productId);
        return productRepository.save(updateExistingProduct(existingProduct, request));

//        return productRepository.findById(productId)
//                .map(existingProduct -> updateExistingProduct(existingProduct, request))
//                .map(productRepository::save)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());
        existingProduct.setDescription(request.getDescription());

        categoryService.getCategoryByName(request.getCategory().getName())
                .ifPresentOrElse(existingProduct::setCategory, () -> {
                    throw new ResourceNotFoundException("Category not found");
                });

        return existingProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);

//        productRepository.findById(id)
//                .ifPresentOrElse(productRepository::delete, () -> {
//                    throw new ProductNotFoundException("Product not found");
//                });
    }

//    @Override
//    public List<Product> getProductsByCategory(String category) {
//        return productRepository.findByCategoryName(category);
//    }
//
//    @Override
//    public List<Product> getProductsByBrand(String brand) {
//        return productRepository.findByBrand(brand);
//    }
//
//    @Override
//    public List<Product> getProductsByName(String name) {
//        return productRepository.findByName(name);
//    }
//
//    @Override
//    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
//        return productRepository.findByCategoryNameAndBrand(category, brand);
//    }
//
//    @Override
//    public List<Product> getProductsByBrandAndName(String brand, String name) {
//        return productRepository.findByBrandAndName(brand, name);
//    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<Product> searchProducts(String brand, String name, String category) {
        Specification<Product> spec = Specification.where(null);

        if (name != null)
            spec = spec.and(ProductSpecifications.hasName(name));

        if (brand != null)
            spec = spec.and(ProductSpecifications.hasBrand(brand));

        if (category != null)
            spec = spec.and(ProductSpecifications.hasCategory(category));

        return productRepository.findAll(spec);
    }
}
