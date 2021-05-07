package com.kuropatin.jdbc;

import com.kuropatin.jdbc.domain.User;
import com.kuropatin.jdbc.repository.UserRepository;
import com.kuropatin.jdbc.repository.UserRepositoryImpl;

import java.sql.Date;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepositoryImpl = new UserRepositoryImpl();

        //findAll
        System.out.println("All Users:");
        for (User user : userRepositoryImpl.findAll()) {
            System.out.println(user);
        }

        //findOne
        System.out.println("One User:");
        try {
            System.out.println(userRepositoryImpl.findOne(100L));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //create
//        System.out.println("Create User:");
//        User user = new User();
//        user.setName("Test");
//        user.setSurname("User");
//        user.setBirthDate(new Date(1234567890L));
//        user.setLogin("test_user");
//        user.setWeight(99.9f);
//        System.out.println(userRepositoryImpl.create(user));

        //update
        System.out.println("Save User:");
        User user = new User();
//        user.setName("Test");
//        user.setSurname("User");
//        user.setBirthDate(new Date(1234567890L));
//        user.setLogin("test_user");
        user.setWeight(71.0f);
        System.out.println(userRepositoryImpl.update(1L, user));

        //delete
        System.out.println("Delete User:");
        userRepositoryImpl.delete(26L);

        //getUserExpensiveCarPrice
//        System.out.println(userRepositoryImpl.getUserExpensiveCarPrice(100));
    }
}