package com.gmail.pashasimonpure.repository.impl;

import com.gmail.pashasimonpure.repository.UserGroupRepository;
import com.gmail.pashasimonpure.repository.model.UserGroup;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserGroupRepositoryImpl extends GeneralRepositoryImpl<UserGroup> implements UserGroupRepository {

    private static UserGroupRepository instance;

    private UserGroupRepositoryImpl() {
    }

    public static UserGroupRepository getInstance() {
        if (instance == null) {
            instance = new UserGroupRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void dropTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DROP TABLE IF EXISTS user_group"
        )) {
            statement.execute();
        }
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS user_group (id INT(11) AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE)"
        )) {
            statement.execute();
        }
    }

    @Override
    public UserGroup add(Connection connection, UserGroup userGroup) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user_group (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, userGroup.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user_group failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userGroup.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating userGroup failed, no ID obtained.");
                }
            }
            return userGroup;
        }
    }

    @Override
    public UserGroup get(Connection connection, Serializable id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user_group WHERE id = ?"
        )) {
            statement.setInt(1, (int) id);
            try (ResultSet resultSet = statement.executeQuery()) {
                UserGroup userGroup = null;
                if (resultSet.next()) {
                    userGroup = UserGroup.newBuilder()
                            .id(resultSet.getInt(1))
                            .name(resultSet.getString(2))
                            .build();
                }
                return userGroup;
            }
        }
    }

    @Override
    public List<UserGroup> findAll(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM user_group"
             )) {
            List<UserGroup> userGroups = new ArrayList<>();
            while (resultSet.next()) {
                userGroups.add(UserGroup.newBuilder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build());
            }
            return userGroups;
        }
    }

    @Override
    public void delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user_group WHERE id = ?"
                )
        ) {
            statement.setInt(1, (int) id);
            statement.execute();
        }
    }

    @Override
    public void update(Connection connection, UserGroup userGroup) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_group SET name=?"
                )
        ) {
            statement.setString(1, userGroup.getName());
            statement.execute();
        }
    }

}