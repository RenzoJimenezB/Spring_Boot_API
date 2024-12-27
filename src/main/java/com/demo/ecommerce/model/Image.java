package com.demo.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;
    private String imageType;
    private String imageUrl;

    @Lob
    private Blob image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
