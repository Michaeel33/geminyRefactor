package com.example.eshopRefactor.Dao;


import com.example.eshopRefactor.Dto.*;

import java.util.List;

public interface FakturaDao {

    PersonalData getPersonalData(long perId);

    FakturaDto getFakturaHistory(long perId);

    PersonalDocuments getPersonalDoc(long perId);

    List<Orders> getOrdersForPerId(long perId);

    List<Items> getItemsForOrder(String orderNo);


}
