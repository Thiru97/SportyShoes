package com.sportyshoes.services;

import com.sportyshoes.model.Order;
import com.sportyshoes.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> getOrder(int id){
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders(){ return  orderRepository.findAll();}

    public void deleteOrder(int id){ orderRepository.deleteById(id); }

    public List<Order> getPurchaseReportByDateAndCategory(LocalDate purchasedDate, String category){
        return orderRepository.getPurchaseReportByDateAndCategory(purchasedDate,category);
    }

    public List<Order> getPurchaseReportByDate(LocalDate purchasedDate){
        return orderRepository.findByPurchasedDate(purchasedDate);
    }

    public List<Order> getPurchaseReportByCategory(String category){
        return orderRepository.getPurchaseReportByCategory(category);
    }



}
