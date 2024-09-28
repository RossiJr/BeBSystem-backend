package org.rossijr.bebsystem.controllers;

import org.rossijr.bebsystem.VOs.v1.AccommodationVO;
import org.rossijr.bebsystem.authentication.AuthUtil;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.AccommodationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/accommodations")
public class AccommodationController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(AccommodationController.class);

    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<Object> createAccommodation(@RequestHeader("Authorization") String authHeader, @RequestBody AccommodationVO obj) {
        try {
            User user = authUtil.validateUserFromToken(authHeader);

            Accommodation accommodation = new Accommodation();
            accommodation.setCreatedBy(user);
            accommodation.setName(obj.getName());
            accommodation.setEmail(obj.getEmail());
            accommodation.setPhoneNumber(obj.getPhoneNumber());

            Accommodation createdAccommodation = accommodationService.createAccommodation(accommodation);
            AccommodationVO response = new AccommodationVO();
            response.setId(createdAccommodation.getId());
            response.setName(createdAccommodation.getName());
            response.setRooms(new ArrayList<>());
            response.setReservationsIds(new ArrayList<>());
            response.setEmail(createdAccommodation.getEmail());
            response.setPhoneNumber(createdAccommodation.getPhoneNumber());
            response.setStatus(createdAccommodation.getStatus());

            logger.info("User {{}} created accommodation with id: {{}}", user.getId(), createdAccommodation.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InsufficientAuthenticationException | BadCredentialsException e) {
            logger.warn("Unauthorized access attempt to create accommodation: {{}}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while creating the accommodation: {{}}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving reservations.");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Object> getAccommodations() {
        try {
            return ResponseEntity.ok(accommodationService.getAccommodations());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal error occurred, contact the administrator");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAccommodationsByUser(@RequestHeader("Authorization") String authHeader) {
        try {
            User user = authUtil.validateUserFromToken(authHeader);
            return ResponseEntity.ok(accommodationService.getAccommodations(user));
        } catch (InsufficientAuthenticationException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving accommodations.");
        }
    }
}
