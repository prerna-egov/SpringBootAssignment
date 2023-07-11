package com.assignment.assignment.controller;

import com.assignment.assignment.model.Address;
import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;
import com.assignment.assignment.producer.Producer;
import com.assignment.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/api")
@RestController
public class UserListController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    private final Producer producer;

    @Autowired
    public UserListController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/users")
    public ResponseEntity<List<String>> createUser(@RequestBody List<User> users) {

        List<String> createStatus = new ArrayList<>();
        for (User user : users) {
            if (userRepository.ifExistingUser(user)){
//                return new ResponseEntity<>("User with the same name and mobile number already exists.", HttpStatus.BAD_REQUEST);
                createStatus.add("User with the same name " + user.getName() + " and mobile number " + user.getMobileNumber()+ " already exists");
        }
            else {

                try {
                    RestTemplate restTemplate = new RestTemplate();
                    String apiUrl = "https://random-data-api.com/api/v2/users?size=1";
                    ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
                    String addressJson = null;
                    String apiResponse = response.getBody();
                    {
                        if (apiResponse != null) {

                            JsonNode rootNode = objectMapper.readTree(apiResponse);
                            JsonNode addressNode = rootNode.get("address");
                            Address address = objectMapper.treeToValue(addressNode, Address.class);
                            user.setAddress(address);
                            System.out.println(user.getAddress().toString());
                        }
                    }

//                    userRepository.createUser(user);
                    producer.push(user);

//                    return new ResponseEntity<>("User was created successfully", HttpStatus.CREATED);
                    createStatus.add("User was created successfully with name " + user.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>(createStatus, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestBody UserSearchCriteria searchCriteria) {
        try {
            User user = userRepository.findUser(searchCriteria);
//            User user1 = userRepository.findUserByActiveStatus(searchCriteria);
            if (user == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/users")
//    public ResponseEntity<User> getUserByActiveStatus(@RequestBody UserSearchCriteria searchCriteria) {
//        try {
//            User user = userRepository.findUserByActiveStatus(searchCriteria);
//            if (user == null) {
//                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

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
