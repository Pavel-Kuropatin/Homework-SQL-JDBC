package com.kuropatin.jdbc.repository;

import com.kuropatin.jdbc.domain.User;

public interface UserRepository extends CrudOperations<Long, User> {

    Double getUserExpensiveCarPrice(long userId);
}