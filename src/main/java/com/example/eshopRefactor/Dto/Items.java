package com.example.eshopRefactor.Dto;

import lombok.Data;

@Data
public class Items {
    private String kategoria;
    private String druhTovaru; // typ položky
    private Double cena;
}