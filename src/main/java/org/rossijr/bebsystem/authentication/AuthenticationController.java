package org.rossijr.bebsystem.authentication;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/authenticate")
public class AuthenticationController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    /**
     * This method is responsible for authenticating the user and returning a JWT token.
     *
     * @param authenticationRequest - The request body containing the username and password
     * @return An object {token: "JWT_TOKEN"} in case of success, or a bad request in case of invalid credentials
     */
    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            logger.info("Authenticating user: {{}}", authenticationRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String jwt = JwtUtil.createToken(userDetails.getUsername());
            logger.info("User authenticated: {{}}", authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException e) {
            logger.info("Invalid username or password for user: {{}}", authenticationRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            logger.error("An error occurred while authenticating user: {{}} / {{}}", authenticationRequest.getUsername(), e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred while authenticating user. Contact the administrator");
        }

    }

    /**
     * This method is responsible for validating a JWT token. Decoding the token and checking if all the fields are valid.
     *
     * @param jwtToken - The JWT token to be validated
     * @return A response entity, code success 200, with a message "Token is valid" in case of success, or a bad request in case of invalid token
     */
    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestParam String jwtToken) {
        try {
            JwtUtil.verifyToken(jwtToken);
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            logger.info("Unsuccessful token validation: {{}}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid token");
        }

    }
}
