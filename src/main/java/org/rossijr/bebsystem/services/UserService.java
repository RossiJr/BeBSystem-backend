package org.rossijr.bebsystem.services;


import org.rossijr.bebsystem.Utils;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.models.UserRole;
import org.rossijr.bebsystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    public User getUserById(UUID id) {
        logger.debug("Retrieving User by id");
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        logger.debug("Retrieving User by email");
        return userRepository.findByEmail(email);
    }

    boolean isPasswordValid(String password){
        logger.debug("Checking for valid password");
        return password != null && password.length() >= 8;
    }

    private boolean isEmailUnique(String email){
        logger.debug("Testing if email is unique");
        return userRepository.findByEmail(email) == null;
    }

    private String hashPassword(String password){
        logger.debug("Hashing password for user");
        return passwordEncoder.encode(password);
    }

    public User createUser(User user){
        if (user == null) {
            throw new IllegalArgumentException("The user cannot be null");
        }
        if (!Utils.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("The email needs to be valid");
        }
        if(!isEmailUnique(user.getEmail())){
            throw new IllegalArgumentException("This email is already in use");
        }
        if (!isPasswordValid(user.getPassword())) {
            throw new IllegalArgumentException("The password needs to be at least 8 characters long");
        }

        logger.debug("Necessary attributes checked. Creating user");

        User obj = new User();
        obj.setEmail(user.getEmail());
        obj.setPassword(hashPassword(user.getPassword()));
        obj.setCreatedAt(Calendar.getInstance());
        obj.setRoles(new HashSet<>());

        UserRole userRole = new UserRole();
        userRole.setUser(obj);
        userRole.setRole(roleService.getRoleByName("ROLE_USER"));
        userRole.setAssignedAt(Calendar.getInstance());

        obj.getRoles().add(userRole);

        return userRepository.save(obj);
    }
}
