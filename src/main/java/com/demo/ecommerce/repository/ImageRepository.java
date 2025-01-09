package com.demo.ecommerce.repository;

import com.demo.ecommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImageKey(String imageKey);

    List<Image> findByProductId(Long productId);
}
