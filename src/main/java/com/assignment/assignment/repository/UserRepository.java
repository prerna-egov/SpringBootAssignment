package com.assignment.assignment.repository;

import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserRepository {

    int createUser(User userInfo) throws JsonProcessingException;

    User findUser(UserSearchCriteria searchCriteria);

    int updateUser(User userInfo);

    int deleteUser(User userInfo);
}
