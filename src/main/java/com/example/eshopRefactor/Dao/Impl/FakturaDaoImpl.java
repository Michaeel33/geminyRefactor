package com.example.eshopRefactor.Dao.Impl;


import com.example.eshopRefactor.Dao.FakturaDao;
import com.example.eshopRefactor.Dto.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Repository
public class FakturaDaoImpl extends NamedParameterJdbcDaoSupport implements FakturaDao {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() {
        setJdbcTemplate(jdbcTemplate);
    }
    @Autowired
    public FakturaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PersonalData getPersonalData(long perId) {

        String sql = "SELECT * FROM personaldata WHERE perId = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapPersonalData(rs), perId);
    }

    public PersonalDocuments getPersonalDoc(long perId){

        String sql = "SELECT pd.obcianskypreukaz, pd.isVerified, c.countryName " +
                "FROM personaldocuments pd " +
                "JOIN country c ON pd.stat = c.countryId " +
                "WHERE pd.perId = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapPersDocument(rs), perId);
    }

    public List<Orders> getOrdersForPerId(long perId){
        String sql = "SELECT orderNumber, price FROM `orders` WHERE perId = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapOrdersForPerId(rs), perId);
    }

    public List<Items> getItemsForOrder(String orderNo){
        String sql = "SELECT i.druhTovaru, i.cena, p.productType FROM itemsfororder ifo JOIN items i ON ifo.itemId = i.itemId JOIN products p ON i.prodId = p.prodId WHERE ifo.orderNumber = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapItemsForOrder(rs), orderNo);
    }

    @Override
    public FakturaDto getFaktura(long perId) {

        FakturaDto fakturaDto = new FakturaDto();
        PersonalData personalDataDto = getPersonalData(perId);
        PersonalDocuments personalDocuments = getPersonalDoc(perId);
        List<Orders> ordersList = getOrdersForPerId(perId);

        for (Orders order : ordersList) {
            List<Items> itemList = getItemsForOrder(order.getOrderNumber());
            order.setOrderedItems(itemList);
        }

        fakturaDto.setPersId(perId);
        fakturaDto.setPersonalData(personalDataDto);
        fakturaDto.setPersonalDocuments(personalDocuments);
        fakturaDto.setOrdersList(ordersList);

        return fakturaDto;

    }


    private PersonalData mapPersonalData(ResultSet rs) throws SQLException {

        PersonalData personalData = new PersonalData();
        personalData.setFirstName(rs.getString("firstName"));
        personalData.setLastName(rs.getString("lastName"));
        personalData.setUlica(rs.getString("ulica"));
        personalData.setMesto(rs.getString("mesto"));
        personalData.setPsc(rs.getString("psc"));

        return personalData;
    }

    private PersonalDocuments mapPersDocument(ResultSet rs) throws SQLException {
        PersonalDocuments document = new PersonalDocuments();
        document.setObcianskypreukaz(rs.getString("obcianskypreukaz"));
        document.setVerified(rs.getInt("isVerified") == 1);
        document.setCountryName(rs.getString("countryName"));
        return document;
    }

    private List<Orders> mapOrdersForPerId(ResultSet rs) throws SQLException{

        List<Orders> ordersList = new ArrayList<>();

        do {
            Orders orders = new Orders();
            orders.setOrderNumber(rs.getString("orderNumber"));
            orders.setPrice(rs.getDouble("price"));
            ordersList.add(orders);
        } while (rs.next());

        return ordersList;

    }

    private List<Items> mapItemsForOrder(ResultSet rs) throws SQLException {
        List<Items> itemsList = new ArrayList<>();

        do {
            Items item = new Items();
            item.setKategoria(rs.getString("productType"));
            item.setDruhTovaru(rs.getString("druhTovaru"));
            item.setCena(rs.getDouble("cena"));
            itemsList.add(item);
        } while (rs.next());

        return itemsList;
    }

}
