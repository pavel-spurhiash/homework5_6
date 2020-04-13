package com.gmail.pashasimonpure.service.model;

public class UserDTO {

    private String username;
    private String password;
    private boolean isActive;
    private int userGroupId;
    private int age;
    private String address;
    private String telephone;

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "UserDTO{ username = " + username +
                ", password = " + password +
                ", isActive = " + isActive +
                ", userGroupId = " + userGroupId +
                ", age = " + age +
                ", address = " + address +
                ", telephone = " + telephone + " }";
    }

}