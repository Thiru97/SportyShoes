package com.sportyshoes.controller;

import com.sportyshoes.exceptions.ProductNotFoundException;
import com.sportyshoes.exceptions.UserNotFoundException;
import com.sportyshoes.model.Image;
import com.sportyshoes.model.Order;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;
import com.sportyshoes.services.ImageService;
import com.sportyshoes.services.OrderService;
import com.sportyshoes.services.ProductService;
import com.sportyshoes.services.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {


    ProductService productService;
    ImageService imageService;
    UserService userService;
    OrderService orderService;

    @Autowired
    public AdminController(ProductService productService,
                           ImageService imageService,
                           UserService userService,
                           OrderService orderService) {
        this.productService = productService;
        this.imageService = imageService;
        this.userService = userService;
        this.orderService= orderService;
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product ){
        productService.saveProduct(product);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PostMapping(value = "/products/image",consumes = {"multipart/form-data"})
    public ResponseEntity<Product> saveProductImage(@RequestParam("productId")int id,
                                                    @RequestParam("image") MultipartFile file, ServletRequest request) throws IOException {


        Optional<Product> product = productService.getProductById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product with id "+id+" is not found");
        } else {
            Image image = imageService.uploadImage(file);
            URI imageURL = ServletUriComponentsBuilder.newInstance()
                    .scheme(request.getScheme())
                    .host(request.getServerName())
                    .port(request.getServerPort())
                    .path("/products/image/{id}")
                    .buildAndExpand(product.get().getProductId()).toUri();
            image.setImageURL(imageURL);

            product.get().setImage(image);
            productService.saveProduct(product.get());

            return new ResponseEntity<Product>(HttpStatus.CREATED);
        }

    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByEmail(@RequestParam("emailId") String emailId){
        System.out.println(emailId);
        User user = userService.findUser(emailId);
        if(user==null){
            throw new UserNotFoundException("User with Email "+emailId+" is not found");
        }
        return new ResponseEntity<User>(user,HttpStatus.FOUND);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id ){
        Optional<Product> product = productService.getProductById(id);

        if(product.isPresent()) {
            productService.deleteProduct(id);
        }else{
            throw new ProductNotFoundException("Product with id "+id+ " is not found" );}

        return new ResponseEntity<Product>(HttpStatus.OK);
    }

    @GetMapping("/purchases/report")
    public List<Order> getPurchaseReport(){
        return orderService.getAllOrders();
    }

    @GetMapping("/purchases/report/filter")
    public ResponseEntity<List<Order>> getFilteredPurchaseReport(@RequestParam(value = "date",required = false) LocalDate purchasedDate,
                                                   @RequestParam(value = "category",required = false) String category){
        List<Order> orderList = new ArrayList<>();

        if(purchasedDate != null && category !=null){
            orderList = orderService.getPurchaseReportByDateAndCategory(purchasedDate,category);
        } else if (purchasedDate != null) {
            orderList = orderService.getPurchaseReportByDate(purchasedDate);
        }else {
            orderList = orderService.getPurchaseReportByCategory(category);
        }

        return new ResponseEntity<List<Order>>(orderList,HttpStatus.OK);

    }
}
