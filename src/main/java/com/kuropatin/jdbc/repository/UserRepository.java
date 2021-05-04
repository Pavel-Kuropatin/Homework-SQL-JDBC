package com.kuropatin.jdbc.repository;

import com.kuropatin.jdbc.domain.User;

import java.util.List;

public interface UserRepository extends CrudOperations<Long, User> {

    List<User> findUsersByQuery(String query);

    Double getUserExpensiveCarPrice(Integer userId);
}