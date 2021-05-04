package com.kuropatin.jdbc.controller;

import com.kuropatin.jdbc.domain.User;
import com.kuropatin.jdbc.repository.UserRepository;
import com.kuropatin.jdbc.repository.UserRepositoryImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ByeController extends HttpServlet {

    private final UserRepository userRepository = new UserRepositoryImpl();

    public ByeController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doRequest(req, resp);
    }

    private void doRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/bye");
            if (dispatcher != null) {
                System.out.println("dispatcher != null");
                req.setAttribute("userName", userRepository.findAll().stream().map(User::getLogin).collect(Collectors.joining(", ")));
                dispatcher.forward(req, resp);
            } else {
                System.out.println("dispatcher = null");
            }
        } catch (ServletException servletException) {
            System.out.println("ServletException");
        } catch (IOException ioException) {
            System.out.println("IOException");
        }
    }
}