package com.sportyshoes.services;

import com.sportyshoes.model.Product;
import com.sportyshoes.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository repository;

    //dependency injection
    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    //saves product
    @Transactional
    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public void saveAllProduct(List<Product> products) {repository.saveAll(products);}

    //delete product
    @Transactional
    public void deleteProduct(Integer id){
    repository.deleteById(id);
    }

    //gets all products
    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    //get product by id
    public Optional<Product> getProductById(Integer id){
        return repository.findById(id);
    }

}
