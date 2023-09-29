package com.sportyshoes.controller;

import com.sportyshoes.exceptions.OrderNotFoundException;
import com.sportyshoes.exceptions.ProductNotFoundException;
import com.sportyshoes.exceptions.UserNotFoundException;
import com.sportyshoes.model.*;
import com.sportyshoes.services.OrderService;
import com.sportyshoes.services.ProductService;
import com.sportyshoes.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/users")
public class UserController {


    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    ProductService productService;
    OrderService orderService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService ,
                          ProductService productService,OrderService orderService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.productService=productService;
        this.orderService=orderService;
    }

    @GetMapping()
    public List<User> getUsers(){
        return userService.getUsers();
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user, HttpServletRequest request) throws MalformedURLException {

        String hashedPwd = passwordEncoder.encode(user.getPassword());

        User tempUser = User.builder().
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                password(hashedPwd).
                authorities(user.getAuthorities()).
                build();

        tempUser.getAuthorities().forEach(authority -> {
            authority.setUser(tempUser);
        });

        User savedUser = userService.saveUser(tempUser);

        URL userURL = ServletUriComponentsBuilder.newInstance().
                scheme(request.getScheme()).
                host(request.getServerName()).
                port(request.getServerPort()).
                path("/users/{id}").
                buildAndExpand(savedUser.getId()).toUri().toURL();

        savedUser.setUserURL(userURL);

        userService.saveUser(savedUser);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> getUserDetailsAfterLogin(Authentication authentication){
        User user = userService.findUser(authentication.getName());

            return new ResponseEntity<User>(user,HttpStatus.OK);
    }


    @PostMapping("/{id}/authority")
    public ResponseEntity<User> addAuthoriy(@PathVariable int id, @RequestBody Authority authority){
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()){
            user.get().getAuthorities().add(authority);
            authority.setUser(user.get());
            userService.saveUser(user.get());
        }
        else {
            throw new UserNotFoundException("User with id "+id+" is not found");
        }
        return new ResponseEntity<User>(user.get(),HttpStatus.CREATED);
    }

    @PostMapping("/products")
    public ResponseEntity<User> addProduct(@RequestBody Cart cart){
        Optional<User> user = userService.findUserById(cart.getUserId());
        Order order = new Order();
        List<Product> productList  =new ArrayList<>();

        List<Integer> productIdList = cart.getProductIdList();

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id "+cart.getUserId()+" is not found");
        }
        order.setPurchasedDate(LocalDate.now());
        order.setUser(user.get());


        productIdList.forEach(productId->{
            Optional<Product> product = productService.getProductById(productId);
            if(product.isEmpty()){
                throw new ProductNotFoundException("Product with Id "+productId+" is not found");
            }
            productList.add(product.get());
        });

        order.setPrice(cart.getTotalAmount());
        order.getProducts().addAll(productList);

        user.get().getOrders().add(order);
        userService.saveUser(user.get());



        return new ResponseEntity<User>(user.get(),HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/products")
    public ResponseEntity<User> cancelOrder(@PathVariable int userId,
                                            @RequestParam(value = "orderId",required = false) int orderId){
        Optional<User> user = userService.findUserById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User ID with "+userId+" is not found");
        }
        Optional<Order> order = orderService.getOrder(orderId);
        if(order.isEmpty()){
            throw new OrderNotFoundException("Order with id "+orderId+" is not found"   );
        }

          user.get().getOrders().remove(order.get());
        orderService.deleteOrder(orderId);

        userService.saveUser(user.get());

        return new ResponseEntity<User>(user.get(),HttpStatus.OK);
    }
}


