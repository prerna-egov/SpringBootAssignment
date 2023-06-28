package com.assignment.assignment.controller;

import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;
import com.assignment.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RestController
public class UserListController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userRepository.createUser(new User(user.getId(), user.getName(), user.getGender(), user.getMobileNumber(), user.getAddress()));
            return new ResponseEntity<>("User was created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestParam int id, @RequestParam String mobileNumber) {
        try {
            User user = userRepository.findUser(new UserSearchCriteria(id, mobileNumber));
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

            userRepository.updateUser(existingUser);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<User> deleteUser(User user) {
        try {
            UserSearchCriteria searchCriteria = new UserSearchCriteria(user.getId(), user.getMobileNumber());
            User existingUser = userRepository.findUser(searchCriteria);
            if (existingUser == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            userRepository.deleteUser(existingUser);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
