package com.kuropatin.jdbc.repository;

import com.kuropatin.jdbc.domain.User;
import com.kuropatin.jdbc.exception.NoSuchEntityException;
import com.kuropatin.jdbc.util.DatabasePropertiesReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.kuropatin.jdbc.util.DatabasePropertiesReader.*;

public class UserRepositoryImpl implements UserRepository {

    private DatabasePropertiesReader databasePropertiesReader = DatabasePropertiesReader.getInstance();

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String BIRTH_DATE = "birth_date";
    public static final String LOGIN = "login";
    public static final String WEIGHT = "weight";

    private String jdbcDriver = databasePropertiesReader.getProperty(DATABASE_DRIVER_NAME);
    private String jdbcURL = databasePropertiesReader.getProperty(DATABASE_URL);
    private String jdbcLogin = databasePropertiesReader.getProperty(DATABASE_LOGIN);
    private String jdbcPassword = databasePropertiesReader.getProperty(DATABASE_PASSWORD);

    @Override
    public List<User> findAll() {
        final String findAllQuery = "SELECT * FROM users ORDER BY id";
        List<User> result = new ArrayList<>();

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try(Connection connection = DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllQuery)
            ) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(ID));
                user.setName(resultSet.getString(NAME));
                user.setSurname(resultSet.getString(SURNAME));
                user.setLogin(resultSet.getString(LOGIN));
                user.setBirthDate(resultSet.getDate(BIRTH_DATE));
                user.setWeight(resultSet.getFloat(WEIGHT));
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User findOne(Long id) {
        final String findById = "SELECT * FROM users WHERE id = ?";

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try(Connection connection = DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(findById)
            ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(ID));
                user.setName(resultSet.getString(NAME));
                user.setSurname(resultSet.getString(SURNAME));
                user.setLogin(resultSet.getString(LOGIN));
                user.setBirthDate(resultSet.getDate(BIRTH_DATE));
                user.setWeight(resultSet.getFloat(WEIGHT));
                return user;
            }
            throw new NoSuchEntityException("No such user with id:" + id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User create(User user) {
        final String createQuery = "INSERT INTO users (name, surname, birth_date, login, weight) VALUES (?, ?, ?, ?, ?)";

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try(Connection connection = DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPassword);
            PreparedStatement statement = connection.prepareStatement(createQuery);
            PreparedStatement lastInsertId = connection.prepareStatement("SELECT CURRVAL('users_id_seq') as last_insert_id;")
            ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setDate(3, new Date(user.getBirthDate().getTime()));
            statement.setString(4, user.getLogin());
            statement.setFloat(5, user.getWeight());
            statement.executeUpdate();

            long insertedId;
            ResultSet lastIdResultSet = lastInsertId.executeQuery();
            if (lastIdResultSet.next()) {
                insertedId = lastIdResultSet.getLong("last_insert_id");
            } else {
                throw new RuntimeException("We cannot read sequence last value during User creation!");
            }
            return findOne(insertedId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public User update(Long id, User user) {
        final String updateQuery = "UPDATE users SET name = ?, surname = ?, birth_date = ?, login = ?, weight = ? WHERE id=?";

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try(Connection connection = DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPassword);
            PreparedStatement statement = connection.prepareStatement(updateQuery)
            ) {
            User currentUser = findOne(id);
            statement.setString(1, user.getName() == null ? currentUser.getName() : user.getName());
            statement.setString(2, user.getSurname() == null ? currentUser.getName() : user.getName());
            statement.setDate(3, new Date(user.getBirthDate() == null ? currentUser.getBirthDate().getTime() : user.getBirthDate().getTime()));
            statement.setString(4, user.getLogin() == null ? currentUser.getLogin() : user.getLogin());
            statement.setFloat(5, user.getWeight() == 0 ? currentUser.getWeight() : user.getWeight());
            statement.setLong(6, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return findOne(id);
    }

    @Override
    public void delete(Long id) {
        final String deleteQuery = "DELETE FROM users WHERE id=?";

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try(Connection connection = DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPassword);
            PreparedStatement statement = connection.prepareStatement(deleteQuery)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        System.out.println("User with id=" + id + " deleted successfully");
    }

    @Override
    public Double getUserExpensiveCarPrice(long userId) {
        final String findPriceFunction = "SELECT get_user_expensive_car(?)";

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcLogin, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(findPriceFunction)
             ) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getDouble("get_user_expensive_car");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }
}