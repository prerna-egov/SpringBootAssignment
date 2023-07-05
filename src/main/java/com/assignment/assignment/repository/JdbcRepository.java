package com.assignment.assignment.repository;

import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;
import com.assignment.assignment.repository.rowmapper.UserRowMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public int createUser(User user) throws JsonProcessingException {

        String addressJson = objectMapper.writeValueAsString(user.getAddress());
        System.out.println(addressJson);
        return jdbcTemplate.update("INSERT INTO users (name, gender, mobileNumber, address, isActive) VALUES( ?, ?, ?, ?::json, ?)",
                 user.getName(), user.getGender(), user.getMobileNumber(), addressJson, user.getIsActive());
    }

    @Override
    public User findUser(UserSearchCriteria searchCriteria) {
        try {
            String sql = "SELECT * FROM users WHERE id = ? AND mobileNumber = ?";
            Object[] params = { searchCriteria.getId(), searchCriteria.getMobileNumber() };

            List<User> userList = jdbcTemplate.query(sql, params, new UserRowMapper());
            if (!userList.isEmpty()) {
                return userList.get(0);
            } else {
                return null;
            }
//            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ? AND mobileNumber = ?",
//                    BeanPropertyRowMapper.newInstance(User.class), searchCriteria.getId(), searchCriteria.getMobileNumber());
//            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int updateUser(User user) {
        return jdbcTemplate.update("UPDATE users SET name = ?, gender = ?, mobileNumber = ?, address = ?, isActive = ?",
                 user.getName(), user.getGender(), user.getMobileNumber(), user.getAddress(), user.getIsActive());
    }

    @Override
    public int deleteUser(User user) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ? AND name = ? AND gender = ? AND mobileNumber = ? AND address = ? AND isActive = ?",
                user.getId(), user.getName(), user.getGender(), user.getMobileNumber(), user.getAddress(), user.getIsActive());
    }

    public boolean ifExistingUser(User user){
        String sql = "SELECT COUNT(*) FROM users WHERE name = ? AND mobileNumber = ? ";
        Object[] params = { user.getName(), user.getMobileNumber() };
        int count = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return count > 0;
    }
}
