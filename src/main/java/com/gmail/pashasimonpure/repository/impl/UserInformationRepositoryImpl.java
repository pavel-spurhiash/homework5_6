package com.gmail.pashasimonpure.repository.impl;

import com.gmail.pashasimonpure.repository.UserInformationRepository;
import com.gmail.pashasimonpure.repository.model.UserInformation;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class UserInformationRepositoryImpl extends GeneralRepositoryImpl<UserInformation> implements UserInformationRepository {

    private static UserInformationRepository instance;

    private UserInformationRepositoryImpl() {
    }

    public static UserInformationRepository getInstance() {
        if (instance == null) {
            instance = new UserInformationRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void dropTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DROP TABLE IF EXISTS user_information"
        )) {
            statement.execute();
        }
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS user_information (user_id INT(11) PRIMARY KEY NOT NULL, address VARCHAR(100)," +
                        "telephone VARCHAR(40), FOREIGN KEY(user_id) REFERENCES user(id))"
        )) {
            statement.execute();
        }
    }

    @Override
    public UserInformation add(Connection connection, UserInformation userInformation) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user_information (user_id, address, telephone) " +
                        "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, userInformation.getUserId());
            statement.setString(2, userInformation.getAddress());
            statement.setString(3, userInformation.getTelephone());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            return userInformation;

        }
    }

    @Override
    public UserInformation get(Connection connection, Serializable id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user_information WHERE user_id = ?"
        )) {
            statement.setInt(1, (int) id);
            try (ResultSet resultSet = statement.executeQuery()) {
                UserInformation userInformation = null;
                if (resultSet.next()) {
                    userInformation = UserInformation.newBuilder()
                            .userId(resultSet.getInt(1))
                            .address(resultSet.getString(2))
                            .telephone(resultSet.getString(3))
                            .build();
                }
                return userInformation;
            }
        }
    }

    @Override
    public Map<Integer, UserInformation> findAll(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM user_information"
             )) {
            Map<Integer, UserInformation> usersInformation = new HashMap<>();
            while (resultSet.next()) {
                usersInformation.put(resultSet.getInt(1), UserInformation.newBuilder()
                        .userId(resultSet.getInt(1))
                        .address(resultSet.getString(2))
                        .telephone(resultSet.getString(3))
                        .build());

            }
            return usersInformation;
        }
    }

    @Override
    public void delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user_information WHERE user_id = ?"
                )
        ) {
            statement.setInt(1, (int) id);
            statement.execute();
        }
    }

    @Override
    public void update(Connection connection, UserInformation userInformation) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_information SET address=?, telephone=?"
                )
        ) {
            statement.setString(1, userInformation.getAddress());
            statement.setString(2, userInformation.getTelephone());
            statement.execute();
        }
    }

}