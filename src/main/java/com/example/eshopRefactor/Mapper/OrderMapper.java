package com.example.eshopRefactor.Mapper;


import com.example.eshopRefactor.Dto.Orders;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    public Orders mapOrdersForPerId(ResultSet rs, int rowNum) throws SQLException {
        Orders orders = new Orders();
        orders.setOrderNumber(rs.getString("orderNumber"));
        orders.setPrice(rs.getDouble("price"));
        return orders;
    }
}