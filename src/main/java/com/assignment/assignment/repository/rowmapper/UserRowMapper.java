package com.assignment.assignment.repository.rowmapper;

import com.assignment.assignment.model.Address;
import com.assignment.assignment.model.User;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRowMapper implements RowMapper<User> {

    @Autowired
    private ObjectMapper mapper;

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(rs.getString("id")));
        user.setName(rs.getString("name"));
        user.setGender(rs.getString("gender"));
        //user.setAddress((Address) rs.getObject("address"));
        String addressJson = rs.getString("address");
        if (addressJson != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Address address = null;
            try {
                address = objectMapper.readValue(addressJson, Address.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            user.setAddress(address);
        }
        user.setMobileNumber(rs.getString("mobileNumber"));
        user.setIsActive(rs.getBoolean("isActive"));
        user.setCreatedTime(rs.getLong("createdTime"));
        return user;
    }
}
