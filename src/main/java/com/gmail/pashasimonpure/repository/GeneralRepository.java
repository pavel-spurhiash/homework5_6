package com.gmail.pashasimonpure.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    void dropTable(Connection connection) throws SQLException;

    void createTable(Connection connection) throws SQLException;

    T add(Connection connection, T t) throws SQLException;

    T get(Connection connection, Serializable id) throws SQLException;

    void delete(Connection connection, Serializable id) throws SQLException;

    void update(Connection connection, T t) throws SQLException;

}