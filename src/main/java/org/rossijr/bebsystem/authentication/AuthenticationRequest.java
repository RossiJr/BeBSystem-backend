package org.rossijr.bebsystem.authentication;

/**
 * Class that defines the request body for the authentication endpoint
 */
public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
