package com.example.eshopRefactor;

import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Service.FakturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faktura") // URL cesta pre faktúry
public class Controller {

    private final FakturaService fakturaService;


    @Autowired
    public Controller(FakturaService fakturaService) {
        this.fakturaService = fakturaService;
    }

    @GetMapping("/{perId}")
    public FakturaDto getFaktura(@PathVariable long perId) {
        return fakturaService.getFakturaHistory(perId);
    }


}