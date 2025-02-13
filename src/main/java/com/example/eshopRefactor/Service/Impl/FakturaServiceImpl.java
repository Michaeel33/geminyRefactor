package com.example.eshopRefactor.Service.Impl;

import com.example.eshopRefactor.Dao.FakturaDao;
import com.example.eshopRefactor.Dao.Impl.PersonalDataDaoImpl;
import com.example.eshopRefactor.Dao.Impl.PersonalDocumentsDaoImpl;
import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Dto.Orders;
import com.example.eshopRefactor.Dto.PersonalData;
import com.example.eshopRefactor.Exception.CustomerNotFoundException;
import com.example.eshopRefactor.Service.FakturaService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public FakturaDto getFakturaHistoryByCustomerName(String firstName, String lastName) {

        List<PersonalData> personalDataList = personalDataDao.getPersonalDataByName(firstName, lastName);

        if (personalDataList.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with name: " + firstName + " " + lastName);
        }
        long perId = personalDataList.get(0).getPerId();

        return getFakturaHistory(perId);
    }

    @Override
    public FakturaDto getFakturaHistoryByCustomerId(String customerId) {
        Long perId = personalDocumentsDao.getPerIdByCustomerId(customerId);

        return getFakturaHistory(perId);
    }

}
