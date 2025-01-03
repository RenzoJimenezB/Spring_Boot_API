package com.demo.ecommerce.repository.specification;

import com.demo.ecommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasBrand(String brand) {
        return (root, query, cb) ->
                cb.equal(root.get("brand"), brand);
    }

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("name"), category);
    }
}
