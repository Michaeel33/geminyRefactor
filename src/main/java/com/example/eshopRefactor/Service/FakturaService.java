package com.example.eshopRefactor.Service;


import com.example.eshopRefactor.Dto.FakturaDto;
import org.springframework.stereotype.Service;

@Service
public interface FakturaService {

    FakturaDto getFaktura(long perId);

}
