package com.example.eshopRefactor.Mapper;

import com.example.eshopRefactor.Dto.PersonalDocuments;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonalDocumentsMapper {

    public PersonalDocuments mapPersonalDocuments(ResultSet rs, int rowNum) throws SQLException {
        PersonalDocuments document = new PersonalDocuments();
        document.setCustomerId(rs.getString("obcianskypreukaz"));
        document.setVerified(rs.getInt("isVerified") == 1);
        document.setCountryName(rs.getString("countryName"));
        return document;
    }
}