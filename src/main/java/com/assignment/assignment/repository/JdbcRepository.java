package com.assignment.assignment.repository;

import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRepository implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createUser(User user) {
        return jdbcTemplate.update("INSERT INTO users (id, name, gender, mobileNumber, address) VALUES(?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getGender(), user.getMobileNumber(), user.getAddress());
    }

    @Override
    public User findUser(UserSearchCriteria searchCriteria) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ? AND mobileNumber = ?",
                    BeanPropertyRowMapper.newInstance(User.class), searchCriteria.getId(), searchCriteria.getMobileNumber());
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int updateUser(User user) {
        return jdbcTemplate.update("UPDATE users SET id = ?, name = ?, gender = ?, mobileNumber = ?, address = ?",
                user.getId(), user.getName(), user.getGender(), user.getMobileNumber(), user.getAddress());
    }

    @Override
    public int deleteUser(User user) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?, name = ?, gender = ?, mobileNumber = ?, address = ?",
                user.getId(), user.getName(), user.getGender(), user.getMobileNumber(), user.getAddress());
    }
}
