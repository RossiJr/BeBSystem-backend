package org.rossijr.bebsystem.controllers;

import org.rossijr.bebsystem.VOs.v1.RoomVO;
import org.rossijr.bebsystem.authentication.AuthUtil;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.Room;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.AccommodationService;
import org.rossijr.bebsystem.services.RoomService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(RoomController.class);

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
                logger.warn("User {{}} tried to create a room in accommodation {{}} that he does not own", user.getId(), accommodation.getId());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed to create a room in this accommodation");
            }

            Room newRoom = roomService.createRoom(room);

            logger.info("User {{}} created room with id {{}}", user.getId(), newRoom.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InsufficientAuthenticationException | BadCredentialsException e) {
            logger.warn("Unauthorized access attempt to create reservation: {{}}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }  catch (Exception e) {
            logger.error("An error occurred while creating the room: {{}}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the room.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getRooms() {
        try {
            return ResponseEntity.ok(roomService.getRooms());
        } catch (Exception e) {
            logger.error("An error occurred while retrieving rooms: {{}}", e.getMessage());
            return ResponseEntity.status(500).body("An internal error occurred, contact the administrator");
        }
    }
}
