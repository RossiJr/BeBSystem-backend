package org.rossijr.bebsystem.controllers;

import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            User obj = userService.createUser(user);
            return ResponseEntity.ok(obj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An internal error occurred, contact the administrator");
        }
    }
}
