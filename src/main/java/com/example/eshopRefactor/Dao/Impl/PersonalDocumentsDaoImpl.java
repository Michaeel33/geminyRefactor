package com.example.eshopRefactor.Dao.Impl;


import com.example.eshopRefactor.Dto.PersonalDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PersonalDocumentsDaoImpl  {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonalDocumentsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PersonalDocuments getPersonalDoc(long perId) {
        String sql = "SELECT pd.perId, pd.obcianskypreukaz, pd.isVerified, c.countryName " +
                "FROM personaldocuments pd " +
                "JOIN country c ON pd.stat = c.countryId " +
                "WHERE pd.perId = ?";
        return jdbcTemplate.queryForObject(sql, this::mapPersDocument, perId);
    }
    public Long getPerIdByCustomerId(String customerId) {
        String sql = "SELECT perId FROM personaldocuments WHERE obcianskypreukaz = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, customerId);
    }



    private PersonalDocuments mapPersDocument(ResultSet rs, int rowNum) throws SQLException {
        PersonalDocuments document = new PersonalDocuments();

        document.setPerId(rs.getLong("perId"));
        document.setCustomerId(rs.getString("obcianskypreukaz"));
        document.setVerified(rs.getInt("isVerified") == 1);
        document.setCountryName(rs.getString("countryName"));

        return document;
    }
}
