package com.demo.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageKey;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
