package org.rossijr.bebsystem.authentication;

import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    @Autowired
    private UserService userService;


    /**
     * Extracts the username from the token and validates it
     * @param authHeader the token to be used
     * @return the user that the token belongs to
     */
    public User validateUserFromToken(String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InsufficientAuthenticationException("Bearer authentication is required");
        }

        String jwtToken = authHeader.substring(7);
        String username = JwtUtil.getUsernameFromToken(jwtToken);

        if (username == null) {
            throw new BadCredentialsException("Invalid token");
        }

        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new BadCredentialsException("Invalid token");
        }

        return user;
    }
}
