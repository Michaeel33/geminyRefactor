package com.example.eshopRefactor.Dto;

import lombok.Data;

@Data
public class PersonalData {
    private Long perId;
    private String firstName;
    private String lastName;
    private String ulica;
    private String mesto;
    private String psc;
}
