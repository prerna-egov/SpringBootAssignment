package com.assignment.assignment.consumer;

import com.assignment.assignment.model.User;
import com.assignment.assignment.repository.JdbcRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Consumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcRepository jdbcRepository;

    public Consumer(ObjectMapper objectMapper, JdbcRepository jdbcRepository){
        this.objectMapper = objectMapper;
        this.jdbcRepository = jdbcRepository;
    }

    @KafkaListener(topics = {"create-user"})
    public void createUser(String userString) throws JsonProcessingException {
        try {
            System.out.println(userString);
            User user = objectMapper.readValue(userString, User.class);
            jdbcRepository.createUser(user);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
