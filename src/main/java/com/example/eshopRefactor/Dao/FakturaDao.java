package com.example.eshopRefactor.Dao;


import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Dto.PersonalData;

public interface FakturaDao {

    PersonalData getPersonalData(long perId);

    FakturaDto getFaktura(long perId);
}
