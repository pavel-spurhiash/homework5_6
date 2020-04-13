package com.gmail.pashasimonpure.repository;

import com.gmail.pashasimonpure.repository.model.UserInformation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    Map<Integer, UserInformation> findAll(Connection connection) throws SQLException;

}
