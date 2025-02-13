package com.example.eshopRefactor.Service.Impl;

import com.example.eshopRefactor.Dao.FakturaDao;
import com.example.eshopRefactor.Dao.Impl.PersonalDataDaoImpl;
import com.example.eshopRefactor.Dao.Impl.PersonalDocumentsDaoImpl;
import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Dto.Orders;
import com.example.eshopRefactor.Dto.PersonalData;
import com.example.eshopRefactor.Exception.CustomerNotFoundException;
import com.example.eshopRefactor.Request.FakturaRequest;
import com.example.eshopRefactor.Service.FakturaService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FakturaServiceImpl  implements FakturaService {

    @Autowired
    private final FakturaDao fakturaDao;

    @Autowired
    private final PersonalDataDaoImpl personalDataDao;

    @Autowired
    private final PersonalDocumentsDaoImpl personalDocumentsDao;



    @Override
    public FakturaDto getFakturaHistory(long perId) {
        FakturaDto fakturaDto = new FakturaDto();
        fakturaDto.setPersId(perId);
        fakturaDto.setPersonalData(fakturaDao.getPersonalData(perId));
        fakturaDto.setPersonalDocuments(fakturaDao.getPersonalDoc(perId));

        List<Orders> ordersList = fakturaDao.getOrdersForPerId(perId);
        for (Orders order : ordersList) {
            order.setOrderedItems(fakturaDao.getItemsForOrder(order.getOrderNumber()));
        }
        fakturaDto.setOrdersList(ordersList);

        return fakturaDto;
    }




    @Override
    public FakturaDto getFakturaHistory(FakturaRequest fakturaRequest) {
        String paramForSearch = fakturaRequest.getParamForSearch();
        String value = fakturaRequest.getValue();

        Long perId = null;

        if ("name".equalsIgnoreCase(paramForSearch)) {
            String[] names = value.split(" ");
            if (names.length != 2) {
                throw new IllegalArgumentException("Meno musí byť v tvare 'Meno Priezvisko'");
            }
            List<PersonalData> personalDataList = personalDataDao.getPersonalDataByName(names[0], names[1]);
            if (personalDataList.isEmpty()) {
                throw new CustomerNotFoundException("Zákazník s menom: " + value + " nebol nájdený.");
            }
            perId = personalDataList.get(0).getPerId();

        } else if ("customerId".equalsIgnoreCase(paramForSearch)) {
            perId = personalDocumentsDao.getPerIdByCustomerId(value);
        } else {
            throw new IllegalArgumentException("Neplatný parameter pre vyhľadávanie: " + paramForSearch);
        }

        if (perId == null) {
            throw new CustomerNotFoundException("Zákazník s ID: " + value + " nebol nájdený."); // Pre customerId
        }

        return getFakturaHistory(perId); // Voláme metódu z FakturaDao
    }

}
