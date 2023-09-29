package com.sportyshoes.repository;

import com.sportyshoes.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    public List<Order> findByPurchasedDate(LocalDate purchasedDate);

    @Query("from Order order JOIN order.products  op where op.category=:category")
    public List<Order> getPurchaseReportByCategory(@Param("category") String category);

    @Query("from Order order JOIN order.products op where order.purchasedDate= :purchasedDate and op.category=:category")
    public List<Order> getPurchaseReportByDateAndCategory(@Param("purchasedDate") LocalDate purchasedDate ,@Param("category") String category);

}
