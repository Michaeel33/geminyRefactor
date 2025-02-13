package com.example.eshopRefactor.Service.Impl;

import com.example.eshopRefactor.Dao.FakturaDao;
import com.example.eshopRefactor.Dto.FakturaDto;
import com.example.eshopRefactor.Dto.Orders;
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


    // Metóda na získanie faktúry
    @Override
    public FakturaDto getFakturaHistory(long perId) {
        FakturaDto fakturaDto = fakturaDao.getFakturaHistory(perId);

        List<Orders> ordersList = fakturaDao.getOrdersForPerId(perId);
        for (Orders order : ordersList) {
            order.setOrderedItems(fakturaDao.getItemsForOrder(order.getOrderNumber()));
        }
        fakturaDto.setOrdersList(ordersList);

        return fakturaDto;
    }

}
