package com.assignment.assignment.controller;

import com.assignment.assignment.model.Address;
import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;
import com.assignment.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


@RequestMapping("/api")
@RestController
public class UserListController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://random-data-api.com/api/v2/users?size=1";
            ResponseEntity<String>  response = restTemplate.getForEntity(apiUrl, String.class);
            String addressJson = null;
            String apiResponse = response.getBody();
             {
                if (apiResponse != null){

                    JsonNode rootNode =  objectMapper.readTree(apiResponse);
                    JsonNode addressNode = rootNode.get("address");
                    Address address = objectMapper.treeToValue(addressNode, Address.class);
                    user.setAddress(address);
                    System.out.println(user.getAddress().toString());
                }
            }

            userRepository.createUser(user);

            return new ResponseEntity<>("User was created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestBody UserSearchCriteria searchCriteria) {
        try {
            User user = userRepository.findUser(searchCriteria);
            if (user == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            UserSearchCriteria searchCriteria = new UserSearchCriteria(user.getId(), user.getMobileNumber());
            User existingUser = userRepository.findUser(searchCriteria);
            if (existingUser == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
//            else if (user.getId() == existingUser.getId()){
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            }
            existingUser.setId(user.getId());
            existingUser.setName(user.getName());
            existingUser.setGender(user.getGender());
            existingUser.setMobileNumber(user.getMobileNumber());
            existingUser.setAddress(user.getAddress());
            existingUser.setCreatedTime(user.getCreatedTime());
            existingUser.setIsActive(user.getIsActive());

            userRepository.updateUser(existingUser);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        try {
            UserSearchCriteria searchCriteria = new UserSearchCriteria(user.getId(), user.getMobileNumber());
            User existingUser = userRepository.findUser(searchCriteria);
            if (existingUser == null) {
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }

            userRepository.deleteUser(existingUser);
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
