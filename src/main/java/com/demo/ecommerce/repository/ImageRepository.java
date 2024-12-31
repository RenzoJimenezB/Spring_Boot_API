package com.demo.ecommerce.repository;

import com.demo.ecommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
