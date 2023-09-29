package com.sportyshoes.controller;

import com.sportyshoes.exceptions.ImageNotFoundException;
import com.sportyshoes.exceptions.ProductNotFoundException;
import com.sportyshoes.services.ImageService;
import com.sportyshoes.model.Image;
import com.sportyshoes.model.Product;
import com.sportyshoes.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {


    ProductService productService;
    ImageService imageService;


    @Autowired
    public ProductController(ProductService productService, ImageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping()
    public List<Product> getAllProducts(){
        return  productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id ){
        Optional<Product> product = productService.getProductById(id);

        return product.orElseThrow(()-> new ProductNotFoundException("Product with id "+id+" is not found"));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id){
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent()){
            int imageId = product.get().getImage().getImageId();

            byte[] imageArray = imageService.downloadImage(imageId);
            return  ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpeg")).body(imageArray);
        }
        else throw new ImageNotFoundException("Image with id "+id+" is not found");
    }


}
