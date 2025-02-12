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
        String sql = "SELECT * FROM personaldata WHERE perId = ?";
        return jdbcTemplate.queryForObject(sql, personalDataMapper::mapPersonalData, perId);
    }

    @Override
    public PersonalDocuments getPersonalDoc(long perId) {
        String sql = "SELECT pd.obcianskypreukaz, pd.isVerified, c.countryName " +
                "FROM personaldocuments pd " +
                "JOIN country c ON pd.stat = c.countryId " +
                "WHERE pd.perId = ?";
        return jdbcTemplate.queryForObject(sql, personalDocumentsMapper::mapPersonalDocuments, perId);
    }

    @Override
    public List<Orders> getOrdersForPerId(long perId) {
        String sql = "SELECT orderNumber, price FROM `orders` WHERE perId = ?";
        return jdbcTemplate.query(sql, orderMapper::mapOrdersForPerId, perId); // Use query for multiple results
    }

    @Override
    public List<Items> getItemsForOrder(String orderNo) {
        String sql = "SELECT i.druhTovaru, i.cena, p.productType FROM itemsfororder ifo JOIN items i ON ifo.itemId = i.itemId JOIN products p ON i.prodId = p.prodId WHERE ifo.orderNumber = ?";
        return jdbcTemplate.query(sql, itemMapper::mapItemsForOrder, orderNo); // Use query for multiple results
    }

    @Override
    public FakturaDto getFaktura(long perId) {
        FakturaDto fakturaDto = new FakturaDto();
        fakturaDto.setPersId(perId);
        fakturaDto.setPersonalData(getPersonalData(perId));
        fakturaDto.setPersonalDocuments(getPersonalDoc(perId));

        List<Orders> ordersList = getOrdersForPerId(perId);
        for (Orders order : ordersList) {
            order.setOrderedItems(getItemsForOrder(order.getOrderNumber()));
        }
        fakturaDto.setOrdersList(ordersList);

        return fakturaDto;
    }
}