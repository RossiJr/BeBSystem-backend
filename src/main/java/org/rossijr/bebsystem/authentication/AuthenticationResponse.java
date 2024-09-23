package org.rossijr.bebsystem.authentication;

/**
 * Class that represents the response of the authentication request.
 */
public class AuthenticationResponse {

    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
