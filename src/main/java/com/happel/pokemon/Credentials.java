package com.happel.pokemon;

public class Credentials {
    public String username;
    public String password;
    public CredentialType credentialType;

    Credentials(String username, String password, CredentialType credentialType) {
        this.username = username;
        this.password = password;
        this.credentialType = credentialType;
    }

    @Override
    public String toString() {
        return "Credentials[" + username + ", " + password + ", " + credentialType.toString() + "]";
    }

    public enum CredentialType {PTC, GOOGLE;}
}

