package com.gmail.pashasimonpure.service.model;

public class UserGroupDTO {

    private String name;

    public UserGroupDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserGroupDTO{ name = " + name + " }";
    }

}