package com.happel.pokemon;

public enum Credentials {
    PTC("", ""),
    GOOGLE("", ""),
    GOOGLE2("", "");

    public final String username;
    public final String password;

    Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
