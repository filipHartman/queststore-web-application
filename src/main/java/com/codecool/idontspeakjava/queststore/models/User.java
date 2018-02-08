package com.codecool.idontspeakjava.queststore.models;

public class User {


    private String firstName;
    private String lastName;
    private int id;
    private String passwordHash;
    private String email;
    private Permissions permission;

    public User(String firstName, String lastName, String passwordHash, String email, Permissions permission) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.permission = permission;
    }

    private User(Builder builder) {
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setId(builder.id);
        setPasswordHash(builder.passwordHash);
        setEmail(builder.email);
        setPermission(builder.permission);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private int id;
        private String passwordHash;
        private String email;
        private Permissions permission;

        public Builder() {
        }

        public Builder setFirstName(String val) {
            firstName = val;
            return this;
        }

        public Builder setLastName(String val) {
            lastName = val;
            return this;
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setPasswordHash(String val) {
            passwordHash = val;
            return this;
        }

        public Builder setEmail(String val) {
            email = val;
            return this;
        }

        public Builder setPermission(Permissions val) {
            permission = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}