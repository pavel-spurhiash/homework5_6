package com.gmail.pashasimonpure.repository.model;

public class User {

    private int id;
    private String username;
    private String password;
    private boolean isActive;
    private int userGroupId;
    private int age;

    private User(Builder builder) {
        id = builder.id;
        username = builder.username;
        password = builder.password;
        isActive = builder.isActive;
        userGroupId = builder.userGroupId;
        age = builder.age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public int getAge() {
        return age;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private int id;
        private String username;
        private String password;
        private boolean isActive;
        private int userGroupId;
        private int age;

        private Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder isActive(boolean val) {
            isActive = val;
            return this;
        }

        public Builder userGroupId(int val) {
            userGroupId = val;
            return this;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}