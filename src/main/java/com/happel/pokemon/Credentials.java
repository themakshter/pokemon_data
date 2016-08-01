package com.happel.pokemon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.happel.pokemon.Utils.println;

public class Credentials {
    public final String username;
    public final String password;
    public final CredentialType credentialType;

    Credentials(String username, String password, CredentialType credentialType) {
        this.username = username;
        this.password = password;
        this.credentialType = credentialType;
    }

    static Credentials buildCredentials(String fileName) {
        println("Building credentials for " + fileName);
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream propertiesStream = loader.getResourceAsStream(fileName)) {
            if (propertiesStream != null) {
                try {
                    properties.load(propertiesStream);
                    String username = properties.getProperty("username");
                    println("Username: " + username);
                    String password = properties.getProperty("password");
                    String type = properties.getProperty("type");
                    return new Credentials(username, password, CredentialType.valueOf(type.toUpperCase()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
        return null;
    }

    public enum CredentialType {PTC, GOOGLE;}
}

