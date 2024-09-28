package org.rossijr.bebsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rossijr.bebsystem.models.Role;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.models.UserRole;
import org.rossijr.bebsystem.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_UserExists() {
        UUID id = UUID.randomUUID();
        User mockUser = new User();
        mockUser.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserById(id);
        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        User user = userService.getUserById(id);
        assertNull(user);
    }

    @Test
    void testGetUserByEmail() {
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        User user = userService.getUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testIsPasswordValid_ValidPassword() {
        String password = "password123";
        boolean isValid = userService.isPasswordValid(password);
        assertTrue(isValid);
    }

    @Test
    void testIsPasswordValid_InvalidPassword() {
        String password = "short";
        boolean isValid = userService.isPasswordValid(password);
        assertFalse(isValid);
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        // Create a user object.
        User user = new User();
        user.setEmail("test@example.com");

        // Simulate behavior: a user already exists with the same email.
        when(userRepository.findByEmail("test@example.com")).thenReturn(new User());

        // Call createUser and expect an IllegalArgumentException to be thrown.
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        // Assert the correct error message is thrown.
        assertEquals("This email is already in use", exception.getMessage());
    }


    @Test
    void testCreateUser_ValidUser(){
        User user1 = new User();
        user1.setEmail("test@example.com");
        user1.setPassword("password123");
        user1.setRoles(new HashSet<>());

        // Simulate an unique email
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        // Simulate a valid password and encode
        when(passwordEncoder.encode(user1.getPassword())).thenReturn("hashedPassword");

        // Simulate saving the user to the repository
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Simulate fetching a role (the fetching behavioiur is not tested here as it is not the responsibility of this class)
        Role role = new Role(0L, "ROLE_USER");
        when(roleService.getRoleByName("ROLE_USER")).thenReturn(role);

        // Simulate setting the user to the UserRole (the Role and UserRole are not tested here as it is not the responsibility of this class)
        UserRole userRole = new UserRole();
        userRole.setId(1L);
        userRole.setUser(user1);
        userRole.setRole(role);
        userRole.setAssignedAt(Calendar.getInstance());
        user1.getRoles().add(userRole);

        User createdUser = userService.createUser(user1);


        // Ensure the basic attributes are set
        assertNotNull(createdUser);
        assertNotNull(createdUser.getEmail());
        assertEquals("test@example.com", createdUser.getEmail()); // Ensure the email is correct
        assertEquals("hashedPassword", createdUser.getPassword()); // Ensure the password is correct
        assertNotNull(createdUser.getPassword());
        assertNotNull(createdUser.getCreatedAt());
        // Ensure the role is set
        assertEquals(1, createdUser.getRoles().size());
        assertEquals("ROLE_USER", createdUser.getRoles().iterator().next().getRole().getName());

        // Ensure that the method "userRepository.save()" was called once
        verify(userRepository, times(1)).save(any(User.class));
        // Ensure that the method ".findByEmail()" was called once
        verify(userRepository, times(1)).findByEmail("test@example.com");
        // Ensure that the method .getRoleByName() was called once
        verify(roleService, times(1)).getRoleByName("ROLE_USER");

    }
}
