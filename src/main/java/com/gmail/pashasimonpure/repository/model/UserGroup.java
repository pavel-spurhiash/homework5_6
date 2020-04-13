package com.gmail.pashasimonpure.repository.model;

public class UserGroup {

    private int id;
    private String name;

    private UserGroup(Builder builder) {
        id = builder.id;
        name = builder.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private int id;
        private String name;

        private Builder() {}

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public UserGroup build() {
            return new UserGroup(this);
        }

    }

}