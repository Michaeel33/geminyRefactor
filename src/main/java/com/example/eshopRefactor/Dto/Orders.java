package com.example.eshopRefactor.Dto;

import lombok.Data;

import java.util.List;

@Data
public class Orders {
    private String orderNumber; // primárny kľúč
    private Double price;
    List<Items> orderedItems;

}