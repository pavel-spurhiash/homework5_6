package com.gmail.pashasimonpure.repository.model;

public class UserInformation {

    private int userId;
    private String address;
    private String telephone;

    private UserInformation(Builder builder) {
        userId = builder.userId;
        address = builder.address;
        telephone = builder.telephone;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private int userId;
        private String address;
        private String telephone;

        private Builder() {}

        public Builder userId(int val) {
            userId = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder telephone(String val) {
            telephone = val;
            return this;
        }

        public UserInformation build() {
            return new UserInformation(this);
        }

    }

}