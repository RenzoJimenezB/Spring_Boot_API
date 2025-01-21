package com.demo.ecommerce.service.product;

import com.demo.ecommerce.dto.product.ProductResponse;
import com.demo.ecommerce.exception.ResourceNotFoundException;
import com.demo.ecommerce.mapper.ProductMapper;
import com.demo.ecommerce.model.Category;
import com.demo.ecommerce.model.Product;
import com.demo.ecommerce.repository.ProductRepository;
import com.demo.ecommerce.repository.specification.ProductSpecifications;
import com.demo.ecommerce.dto.product.AddProductRequest;
import com.demo.ecommerce.dto.product.ProductUpdateRequest;
import com.demo.ecommerce.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final ProductMapper productMapper;


    @Value("${cloudfront.domain}")
    private String cloudFrontDomain;


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


    @Override
    public Page<ProductResponse> searchProducts(String name, String brand, String category, int page, int size) {
        Specification<Product> spec = buildProductSpecification(name, brand, category);
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findAll(spec, pageable);
        return productPage.map(product -> productMapper.toProductResponse(product, cloudFrontDomain));
    }


    @Override
    public Long countProducts(String name, String brand, String category) {
        Specification<Product> spec = buildProductSpecification(name, brand, category);
        return productRepository.count(spec);
    }

    private Specification<Product> buildProductSpecification(
            String name,
            String brand,
            String category) {
        Specification<Product> spec = Specification.where(null);

        if (name != null)
            spec = spec.and(ProductSpecifications.hasName(name));

        if (brand != null)
            spec = spec.and(ProductSpecifications.hasBrand(brand));

        if (category != null)
            spec = spec.and(ProductSpecifications.hasCategory(category));

        return spec;
    }


    @Override
    public ProductResponse getProductResponseById(Long id) {
        Product product = getProductEntityById(id);
        return productMapper.toProductResponse(product, cloudFrontDomain);
    }


    public Product getProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        Product existingProduct = getProductEntityById(productId);
        return productRepository.save(updateExistingProduct(existingProduct, request));
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
        Product existingProduct = getProductEntityById(id);
        existingProduct.setCategory(null);
        productRepository.delete(existingProduct);
    }
}
