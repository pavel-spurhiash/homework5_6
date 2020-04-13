package com.gmail.pashasimonpure.repository;

import com.gmail.pashasimonpure.repository.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends GeneralRepository<User> {

    List<User> findAll(Connection connection) throws SQLException;

}
