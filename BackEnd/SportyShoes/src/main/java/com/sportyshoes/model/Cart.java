package com.sportyshoes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    int userId;

    List<Integer> productIdList = new ArrayList<>();

    int totalAmount ;

}
