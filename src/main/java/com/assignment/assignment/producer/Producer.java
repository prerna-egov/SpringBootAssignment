package com.assignment.assignment.producer;

import com.assignment.assignment.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Autowired
    private ObjectMapper objectmapper;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void push(User user) throws JsonProcessingException {
        String userJson = objectmapper.writeValueAsString(user);
        kafkaTemplate.send("create-user", userJson);
    }
}
