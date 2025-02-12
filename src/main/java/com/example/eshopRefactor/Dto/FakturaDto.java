package com.example.eshopRefactor.Dto;


import lombok.Data;

import java.util.List;
@Data
public class FakturaDto {

    private long persId;

    PersonalData personalData;

    PersonalDocuments personalDocuments;

    List<Orders> ordersList;

}
