package com.example.eshopRefactor.Dto;

import lombok.Data;

@Data
public class PersonalDocuments {
    private Long perId;
    private String customerId;
    private String countryName; // ID krajiny ako cudzí kľúč
    private boolean isVerified;
}