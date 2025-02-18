package com.demo.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    @Column(columnDefinition = "DECIMAL(10, 2)")
    private BigDecimal price;

    @Column(columnDefinition = "SMALLINT")
    private int stock;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    public Product(String name, String brand, BigDecimal price, int stock, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
    }
}
