package com.example.eshopRefactor.Service.Impl;

import com.example.eshopRefactor.Dao.FakturaDao;
import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Service.FakturaService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FakturaServiceImpl  implements FakturaService {

    @Autowired
    private final FakturaDao fakturaDao;


    // Metóda na získanie faktúry
    public FakturaDto getFaktura(long perId) {
        return fakturaDao.getFaktura(perId);
    }

}
