package com.example.eshopRefactor.Dao.Impl;

import com.example.eshopRefactor.Dto.PersonalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonalDataDaoImpl  {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonalDataDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PersonalData getPersonalData(long perId) {
        String sql = "SELECT * FROM personaldata WHERE perId = ?";
        return jdbcTemplate.queryForObject(sql, this::mapPersonalData, perId);
    }

    public List<PersonalData> getPersonalDataByName(String firstName, String lastName) {
        String sql = "SELECT * FROM personaldata WHERE firstName = ? AND lastName = ?";
        return jdbcTemplate.query(sql, this::mapPersonalData, firstName, lastName);
    }


    private PersonalData mapPersonalData(ResultSet rs, int rowNum) throws SQLException {
        PersonalData personalData = new PersonalData();
        personalData.setPerId(rs.getLong("perId")); // Pridajte načítanie perId
        personalData.setFirstName(rs.getString("firstName"));
        personalData.setLastName(rs.getString("lastName"));
        personalData.setUlica(rs.getString("ulica"));
        personalData.setMesto(rs.getString("mesto"));
        personalData.setPsc(rs.getString("psc"));
        return personalData;
    }
}
