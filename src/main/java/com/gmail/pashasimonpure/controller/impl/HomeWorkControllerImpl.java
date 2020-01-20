package com.gmail.pashasimonpure.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import com.gmail.pashasimonpure.controller.HomeWorkController;
import com.gmail.pashasimonpure.service.UserService;
import com.gmail.pashasimonpure.service.impl.UserServiceImpl;
import com.gmail.pashasimonpure.service.model.UserDTO;
import com.gmail.pashasimonpure.service.model.UserGroupDTO;
import com.gmail.pashasimonpure.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.soap.SOAPBinding;

public class HomeWorkControllerImpl implements HomeWorkController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static HomeWorkController instance;

    //USER UPDATE OPTIONS
    private static final int MAX_YOUNG_AGE = 27;
    private static final int MIN_AGE_TO_UPDATE = 30;
    private static final int MAX_AGE_TO_UPDATE = 33;
    private static final boolean UPDATED_ACTIVITY = false;

    //USER GENERATE OPTIONS:
    private static final Integer MIN_AGE = 25;
    private static final Integer MAX_AGE = 35;
    private static final Integer USERS_COUNT = 30;

    //GROUP USER GENERATE OPTIONS:
    private static final Integer GROUPS_COUNT = 3;

    private HomeWorkControllerImpl() {
    }

    public static HomeWorkController getInstance() {
        if (instance == null) {
            instance = new HomeWorkControllerImpl();
        }
        return instance;
    }

    @Override
    public void runTask() {

        UserService userService = UserServiceImpl.getInstance();

        userService.drop();
        userService.init();

        for (int i = 0; i < GROUPS_COUNT; i++) {
            userService.addUserGroup(new UserGroupDTO("group#" + RandomUtil.getRandomIntAbs()));
        }

        userService.addAllUsers(generateUsers(USERS_COUNT));


        List<UserDTO> users = userService.findAllUsers();

        for (UserDTO user : users) {
            logger.info(user.toString());
        }

        List<UserGroupDTO> usersGroupDTO = userService.findAllUserGroup();

        for (UserGroupDTO userGroupDTO : usersGroupDTO) {
            logger.info("Count users in " + userGroupDTO.getName() + ": " + userService.countUsersInGroupWithName(userGroupDTO.getName()) + " users.");
        }

        logger.info("Count deleted users with age < " + MAX_YOUNG_AGE + ": " + userService.deleteUsersYoungUsers(MAX_YOUNG_AGE) + " users.");

        userService.updateUserActivityByAge(MIN_AGE_TO_UPDATE, MAX_AGE_TO_UPDATE, UPDATED_ACTIVITY);

    }

    private List<UserDTO> generateUsers(Integer count) {

        List<UserDTO> users = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("user#" + RandomUtil.getRandomIntAbs());
            userDTO.setPassword(Integer.toString(RandomUtil.getRandomIntAbs()));
            userDTO.setActive(RandomUtil.getRandomBoolean());
            userDTO.setUserGroupId(RandomUtil.getRandomIntBetween(1, GROUPS_COUNT));
            userDTO.setAge(RandomUtil.getRandomIntBetween(MIN_AGE, MAX_AGE));
            userDTO.setAddress("address#" + RandomUtil.getRandomIntAbs());
            userDTO.setTelephone(Integer.toString(RandomUtil.getRandomIntAbs()));

            users.add(userDTO);
        }

        return users;
    }

}