package com.example.eshopRefactor.Service;


import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Request.FakturaRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FakturaService {

    FakturaDto getFakturaHistory(long perId);

    List<FakturaDto> getFakturaHistory(FakturaRequest fakturaRequest);

}
