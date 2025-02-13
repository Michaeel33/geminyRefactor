package com.example.eshopRefactor;

import com.example.eshopRefactor.Dao.Impl.PersonalDataDaoImpl;
import com.example.eshopRefactor.Dao.Impl.PersonalDocumentsDaoImpl;
import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Dto.PersonalData;
import com.example.eshopRefactor.Exception.CustomerNotFoundException;
import com.example.eshopRefactor.Request.FakturaRequest;
import com.example.eshopRefactor.Service.FakturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faktura") // URL cesta pre faktúry
public class Controller {

    private final FakturaService fakturaService;

    private final PersonalDataDaoImpl personalDataDao;

    private final PersonalDocumentsDaoImpl personalDocumentsDao;



    @Autowired
    public Controller(FakturaService fakturaService, PersonalDataDaoImpl personalDataDao, PersonalDocumentsDaoImpl personalDocumentsDao) {
        this.fakturaService = fakturaService;
        this.personalDataDao = personalDataDao;
        this.personalDocumentsDao = personalDocumentsDao;
    }

    @GetMapping("/{perId}")
    public FakturaDto getFaktura(@PathVariable long perId) {
        return fakturaService.getFakturaHistory(perId);
    }

    @PostMapping("/fakturaHistory")
    public List<FakturaDto> getFakturaHistory(@RequestBody FakturaRequest fakturaRequest) {
        return fakturaService.getFakturaHistory(fakturaRequest);
    }




}