package org.rossijr.bebsystem.controllers;

import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            logger.info("Request received to create user with email: {{}}", user != null ? user.getEmail() : null);
            User obj = userService.createUser(user);
            logger.info("User created successfully with email and id: {{}} / {{}}", obj.getEmail(), obj.getId());
            return ResponseEntity.ok(obj);
        } catch (IllegalArgumentException e) {
            logger.warn("User not created due to: {{}}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("An internal error occurred while creating the user: {{}}" , e.getMessage());
            return ResponseEntity.status(500).body("An internal error occurred, contact the administrator");
        }
    }
}
