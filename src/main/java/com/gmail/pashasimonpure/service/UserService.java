package com.gmail.pashasimonpure.service;

import com.gmail.pashasimonpure.service.model.UserDTO;
import com.gmail.pashasimonpure.service.model.UserGroupDTO;

import java.util.List;


public interface UserService {

    void drop();

    void init();


    void addUserGroup(UserGroupDTO userGroupDTO);

    Integer countUsersInGroupWithName(String groupName);

    List<UserGroupDTO> findAllUserGroup();


    void addUser(UserDTO userDTO);

    void addAllUsers(List<UserDTO> usersDTO);

    List<UserDTO> findAllUsers();


    Integer deleteUsersYoungUsers(Integer age);

    void updateUserActivityByAge(Integer minAge, Integer maxAge, boolean isActive);

}
