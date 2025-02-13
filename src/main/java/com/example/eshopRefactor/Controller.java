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
    public FakturaDto getFakturaHistory(@RequestBody FakturaRequest fakturaRequest) {
        return fakturaService.getFakturaHistory(fakturaRequest); // Delegujeme na servis
    }

//    @PostMapping("/fakturaHistory")  // Správny endpoint pre POST
//    public FakturaDto getFakturaHistory(@RequestBody FakturaRequest fakturaRequest) {
//        String paramForSearch = fakturaRequest.getParamForSearch();
//        String value = fakturaRequest.getValue();
//
//        Long perId = null;
//
//        if ("name".equalsIgnoreCase(paramForSearch)) {
//            String[] names = value.split(" ");
//            if (names.length != 2) {
//                throw new IllegalArgumentException("Meno musí byť v tvare 'Meno Priezvisko'");
//            }
//            List<PersonalData> personalDataList = personalDataDao.getPersonalDataByName(names[0], names[1]);
//            if (personalDataList.isEmpty()) {
//                throw new CustomerNotFoundException("Zákazník s menom: " + value + " nebol nájdený.");
//            }
//            perId = personalDataList.get(0).getPerId();
//
//        } else if ("customerId".equalsIgnoreCase(paramForSearch)) {
//            perId = personalDocumentsDao.getPerIdByCustomerId(value);
//        } else {
//            throw new IllegalArgumentException("Neplatný parameter pre vyhľadávanie: " + paramForSearch);
//        }
//
//        if (perId == null) {
//            throw new CustomerNotFoundException("Zákazník s ID: " + value + " nebol nájdený."); // Pre customerId
//        }
//
//        return fakturaService.getFakturaHistory(perId);
//    }


}