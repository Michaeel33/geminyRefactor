package com.example.eshopRefactor.Mapper;

import com.example.eshopRefactor.Dto.PersonalData;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonalDataMapper {
    public PersonalData mapPersonalData(ResultSet rs, int rowNum) throws SQLException {
        PersonalData personalData = new PersonalData();
        personalData.setFirstName(rs.getString("firstName"));
        personalData.setLastName(rs.getString("lastName"));
        personalData.setUlica(rs.getString("ulica"));
        personalData.setMesto(rs.getString("mesto"));
        personalData.setPsc(rs.getString("psc"));
        return personalData;
    }
}
