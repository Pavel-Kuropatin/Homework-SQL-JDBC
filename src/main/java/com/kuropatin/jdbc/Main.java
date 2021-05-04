package com.kuropatin.jdbc;

import com.kuropatin.jdbc.domain.User;
import com.kuropatin.jdbc.repository.UserRepository;
import com.kuropatin.jdbc.repository.UserRepositoryImpl;

public class Main {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();

        //Find all users
//        for (User user : userRepository.findAll()) {
//            System.out.println(user);
//        }

        //Find one
//        try {
//            System.out.println(userRepository.findOne(100L));
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        //save
//        User user = new User();
//        user.setName("Test");
//        user.setSurname("Save");
//        user.setLogin("test_save_2");
//        user.setWeight(110f);
//        user.setBirthDate(new Date(12000000L));
//        System.out.println(userRepository.save(user));

        //check function call
        //System.out.println(userRepository.getUserExpensiveCarPrice(100));
    }
}