package com.gmail.pashasimonpure.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gmail.pashasimonpure.repository.ConnectionRepository;
import com.gmail.pashasimonpure.repository.UserGroupRepository;
import com.gmail.pashasimonpure.repository.UserInformationRepository;
import com.gmail.pashasimonpure.repository.UserRepository;
import com.gmail.pashasimonpure.repository.impl.ConnectionRepositoryImpl;
import com.gmail.pashasimonpure.repository.impl.UserGroupRepositoryImpl;
import com.gmail.pashasimonpure.repository.impl.UserInformationRepositoryImpl;
import com.gmail.pashasimonpure.repository.impl.UserRepositoryImpl;
import com.gmail.pashasimonpure.repository.model.User;
import com.gmail.pashasimonpure.repository.model.UserGroup;
import com.gmail.pashasimonpure.repository.model.UserInformation;
import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.model.UserDTO;
import com.gmail.pashasimonpure.service.model.UserGroupDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    private static UserService instance;

    private static final String USER_TABLE_NAME = "user";
    private static final String USER_GROUP_TABLE_NAME = "user_group";
    private static final String USER_INFORMATION_TABLE_NAME = "user_information";

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void drop() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                userInformationRepository.dropTable(connection);
                userRepository.dropTable(connection);
                userGroupRepository.dropTable(connection);

                connection.commit();
                logger.debug("tables deleted.");
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("unable to delete tables.");
        }
    }

    @Override
    public void init() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                userGroupRepository.createTable(connection);
                userRepository.createTable(connection);
                userInformationRepository.createTable(connection);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("unable create tables");
        }
    }

    @Override
    public void addUserGroup(UserGroupDTO userGroupDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                UserGroup userGroup = UserGroup
                        .newBuilder()
                        .name(userGroupDTO.getName())
                        .build();

                userGroupRepository.add(connection, userGroup);
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("User group failed to add.");
        }
    }

    @Override
    public List<UserGroupDTO> findAllUserGroup() {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                List<UserGroupDTO> usersGroupDTO = new ArrayList<>();
                List<UserGroup> usersGroup = userGroupRepository.findAll(connection);

                for (UserGroup userGroup : usersGroup) {

                    UserGroupDTO ugDTO = new UserGroupDTO(userGroup.getName());
                    usersGroupDTO.add(ugDTO);

                }

                connection.commit();

                return usersGroupDTO;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Finding users failed.");
        }
        return null;

    }

    @Override
    public Integer countUsersInGroupWithName(String groupName) {

        int count = 0;

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                List<UserGroup> userGroupList = userGroupRepository.findAll(connection);
                List<User> userList = userRepository.findAll(connection);

                for (UserGroup userGroup : userGroupList) {

                    if (userGroup.getName().equals(groupName)) {

                        for (User user : userList) {
                            if (user.getUserGroupId() == userGroup.getId()) {
                                count = count + 1;
                            }
                        }

                    }

                }

                connection.commit();
                return count;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return -1;

    }

    @Override
    public void addUser(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                User user = User.newBuilder()
                        .username(userDTO.getUsername())
                        .password(userDTO.getPassword())
                        .isActive(userDTO.isActive())
                        .userGroupId(userDTO.getUserGroupId())
                        .age(userDTO.getAge())
                        .build();

                UserInformation userInformation = UserInformation
                        .newBuilder()
                        .address(userDTO.getAddress())
                        .telephone(userDTO.getTelephone())
                        .build();

                user = userRepository.add(connection, user);

                userInformation.setUserId(user.getId());
                userInformationRepository.add(connection, userInformation);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("user add operation filed.");
        }
    }

    @Override
    public void addAllUsers(List<UserDTO> usersDTO) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                for (UserDTO userDTO : usersDTO) {

                    User user = User.newBuilder()
                            .username(userDTO.getUsername())
                            .password(userDTO.getPassword())
                            .isActive(userDTO.isActive())
                            .userGroupId(userDTO.getUserGroupId())
                            .age(userDTO.getAge())
                            .build();

                    UserInformation userInformation = UserInformation
                            .newBuilder()
                            .address(userDTO.getAddress())
                            .telephone(userDTO.getTelephone())
                            .build();

                    user = userRepository.add(connection, user);

                    userInformation.setUserId(user.getId());
                    userInformationRepository.add(connection, userInformation);

                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("user add operation filed.");
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {

                List<UserDTO> usersDTO = new ArrayList<>();

                List<User> users = userRepository.findAll(connection);
                List<UserGroup> userGroups = userGroupRepository.findAll(connection);
                Map<Integer, UserInformation> usersInformation = userInformationRepository.findAll(connection);

                for (User user : users) {

                    UserDTO uDTO = new UserDTO();
                    uDTO.setUsername(user.getUsername());
                    uDTO.setPassword(user.getPassword());
                    uDTO.setActive(user.isActive());
                    uDTO.setAge(user.getAge());
                    uDTO.setUserGroupId(user.getUserGroupId());
                    uDTO.setAddress(usersInformation.get(user.getId()).getAddress());
                    uDTO.setTelephone(usersInformation.get(user.getId()).getTelephone());

                    usersDTO.add(uDTO);

                }

                connection.commit();

                return usersDTO;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Finding users failed.");
        }
        return null;
    }


    @Override
    public Integer deleteUsersYoungUsers(Integer age) {
        Integer count = 0;
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.findAll(connection);

                for (User user : users) {

                    if (user.getAge() < 27) {
                        count = count + 1;
                        userInformationRepository.delete(connection, user.getId());
                        userRepository.delete(connection, user.getId());
                    }
                }

                connection.commit();
                return count;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Failed to delete users.");
        }
        return -1;
    }

    @Override
    public void updateUserActivityByAge(Integer minAge, Integer maxAge, boolean isActive) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.findAll(connection);

                for (User user : users) {

                    if (user.getAge() > minAge && user.getAge() < maxAge) {
                        userRepository.update(connection, User.newBuilder().id(user.getId())
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .isActive(isActive)
                                .userGroupId(user.getUserGroupId())
                                .age(user.getAge())
                                .build());
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Failed to update users.");
        }
    }

}