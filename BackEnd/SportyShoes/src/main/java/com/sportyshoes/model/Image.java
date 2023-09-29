package com.sportyshoes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.net.URI;
import java.net.URL;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @Column(name ="name")
    private String name;

    @Column(name ="type")
    private String type;

    @JsonIgnore
    @Lob
    @Column(name ="image_data" , length = 1000)
    private byte[] imageData;

    @Column(name = "image_url")
    URI imageURL;
}
