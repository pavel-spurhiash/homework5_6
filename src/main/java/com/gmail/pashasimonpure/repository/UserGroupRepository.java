package com.gmail.pashasimonpure.repository;

import com.gmail.pashasimonpure.repository.model.UserGroup;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserGroupRepository extends GeneralRepository<UserGroup> {

    List<UserGroup> findAll(Connection connection) throws SQLException;

}