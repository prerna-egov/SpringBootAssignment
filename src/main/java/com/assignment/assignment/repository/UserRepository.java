package com.assignment.assignment.repository;

import com.assignment.assignment.model.User;
import com.assignment.assignment.model.UserSearchCriteria;

public interface UserRepository {

    int createUser(User userInfo);

    User findUser(UserSearchCriteria searchCriteria);

    int updateUser(User userInfo); //updating id is mandatory

    int deleteUser(User userInfo); //deleting id is mandatory
}
