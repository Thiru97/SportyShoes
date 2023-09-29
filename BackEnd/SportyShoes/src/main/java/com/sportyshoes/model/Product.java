package com.sportyshoes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;


    @JsonIgnore
    @ManyToMany(mappedBy = "products",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE },
            fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    private String name;

    @Column(name = "brand_name")
    private String brandName;
    private String color;
    private int size;

    private String gender;

    private String category;
    private int price;


}
