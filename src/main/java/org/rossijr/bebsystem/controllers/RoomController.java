package org.rossijr.bebsystem.controllers;

import org.rossijr.bebsystem.VOs.v1.RoomVO;
import org.rossijr.bebsystem.authentication.AuthUtil;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.AccommodationService;
import org.rossijr.bebsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<Object> createRoom(@RequestHeader("Authorization") String authHeader, @RequestBody RoomVO room) {
        try {
            User user = authUtil.validateUserFromToken(authHeader);
            Accommodation accommodation = accommodationService.getAccommodationById(room.getAccommodationId());
            if (accommodation == null) {
                return ResponseEntity.badRequest().body("Accommodation not found");
            }
            if (!accommodation.getCreatedBy().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed to create a room in this accommodation");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(room));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InsufficientAuthenticationException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the room.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getRooms() {
        try {
            return ResponseEntity.ok(roomService.getRooms());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An internal error occurred, contact the administrator");
        }
    }
}
