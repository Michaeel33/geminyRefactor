package com.example.eshopRefactor.Dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemsForOrder {
    private Integer id;
    private String orderNumber;
    private Integer itemId;
    private List<Items> items;
}