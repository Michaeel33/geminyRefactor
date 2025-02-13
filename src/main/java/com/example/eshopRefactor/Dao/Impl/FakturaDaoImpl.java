package com.example.eshopRefactor.Dao.Impl;


import com.example.eshopRefactor.Dao.FakturaDao;
import com.example.eshopRefactor.Dto.*;
import com.example.eshopRefactor.Mapper.ItemMapper;
import com.example.eshopRefactor.Mapper.OrderMapper;
import com.example.eshopRefactor.Mapper.PersonalDataMapper;
import com.example.eshopRefactor.Mapper.PersonalDocumentsMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FakturaDaoImpl extends NamedParameterJdbcDaoSupport implements FakturaDao {

    private static final String SQL_PERSONAL_DATA = "SELECT * FROM personaldata WHERE perId = ?";
    private static final String SQL_PERSONAL_DOCUMENTS = "SELECT pd.obcianskypreukaz, pd.isVerified, c.countryName " +
            "FROM personaldocuments pd " +
            "JOIN country c ON pd.stat = c.countryId " +
            "WHERE pd.perId = ?";
    private static final String SQL_ORDERS_FOR_PER_ID = "SELECT orderNumber, price FROM `orders` WHERE perId = ?";
    private static final String SQL_ITEMS_FOR_ORDER = "SELECT i.druhTovaru, i.cena, p.productType FROM itemsfororder ifo JOIN items i ON ifo.itemId = i.itemId JOIN products p ON i.prodId = p.prodId WHERE ifo.orderNumber = ?";

    private final JdbcTemplate jdbcTemplate;
    private final PersonalDataMapper personalDataMapper;
    private final PersonalDocumentsMapper personalDocumentsMapper;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;

    @PostConstruct
    private void initialize() {
        setJdbcTemplate(jdbcTemplate);
    }

    @Autowired
    public FakturaDaoImpl(JdbcTemplate jdbcTemplate, PersonalDataMapper personalDataMapper, PersonalDocumentsMapper personalDocumentsMapper, OrderMapper orderMapper, ItemMapper itemMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.personalDataMapper = personalDataMapper;
        this.personalDocumentsMapper = personalDocumentsMapper;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public PersonalData getPersonalData(long perId) {
        return jdbcTemplate.query(SQL_PERSONAL_DATA, personalDataMapper::mapPersonalData, perId).stream().findFirst().orElse(null);
    }

    @Override
    public PersonalDocuments getPersonalDoc(long perId) {
        return jdbcTemplate.query(SQL_PERSONAL_DOCUMENTS, personalDocumentsMapper::mapPersonalDocuments, perId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Orders> getOrdersForPerId(long perId) {
        return jdbcTemplate.query(SQL_ORDERS_FOR_PER_ID, orderMapper::mapOrdersForPerId, perId);
    }

    @Override
    public List<Items> getItemsForOrder(String orderNo) {
        return jdbcTemplate.query(SQL_ITEMS_FOR_ORDER, itemMapper::mapItemsForOrder, orderNo);
    }



    @Override
    public FakturaDto getFakturaHistory(long perId) {
        //  Logika presunutá do FakturaServiceImpl
        FakturaDto fakturaDto = new FakturaDto();
        fakturaDto.setPersId(perId);
        fakturaDto.setPersonalData(getPersonalData(perId));
        fakturaDto.setPersonalDocuments(getPersonalDoc(perId));
        return fakturaDto;
    }


}