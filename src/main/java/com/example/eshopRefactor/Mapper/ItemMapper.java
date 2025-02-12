package com.example.eshopRefactor.Mapper;

import com.example.eshopRefactor.Dto.Items;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemMapper {
    public Items mapItemsForOrder(ResultSet rs, int rowNum) throws SQLException {
        Items item = new Items();
        item.setKategoria(rs.getString("productType"));
        item.setDruhTovaru(rs.getString("druhTovaru"));
        item.setCena(rs.getDouble("cena"));
        return item;
    }
}