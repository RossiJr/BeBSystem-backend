package org.rossijr.bebsystem.controllers;

import org.rossijr.bebsystem.VOs.v1.reservations.ReservationChartVO;
import org.rossijr.bebsystem.VOs.v1.reservations.ReservationVO;
import org.rossijr.bebsystem.authentication.AuthUtil;
import org.rossijr.bebsystem.models.Reservation;
import org.rossijr.bebsystem.models.Room;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<Object> getReservations(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "days", required = false) Integer days)  {

        try {
            User user = authUtil.validateUserFromToken(authHeader);
            List<ReservationChartVO> reservations;

            if(days != null) {
                if (days > 0) {
                    reservations = reservationService.getReservationsByUserIdAndDays(user.getId(), days).stream()
                            .map(reservation -> new ReservationChartVO(
                                    reservation.getId(),
                                    reservation.getRooms().stream().collect(Collectors.toMap(Room::getId, Room::getName)),
                                    reservation.getStatus(),
                                    reservation.getCheckIn(),
                                    reservation.getCheckOut(),
                                    reservation.getReservationCompany(),
                                    reservation.getReservationCode()
                            )).collect(Collectors.toList());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Days must be greater than 0");
                }
            } else {
                reservations = reservationService.getReservationsByUserId(user.getId()).stream()
                        .map(reservation -> new ReservationChartVO(
                                reservation.getId(),
                                reservation.getRooms().stream().collect(Collectors.toMap(Room::getId, Room::getName)),
                                reservation.getStatus(),
                                reservation.getCheckIn(),
                                reservation.getCheckOut(),
                                reservation.getReservationCompany(),
                                reservation.getReservationCode()
                        )).collect(Collectors.toList());
            }

            return ResponseEntity.ok(reservations);
        } catch (InsufficientAuthenticationException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving reservations.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createReservation(@RequestHeader("Authorization") String authHeader, @RequestBody ReservationVO reservationVO) {
        try {
            User user = authUtil.validateUserFromToken(authHeader);

            Reservation reservation = new Reservation();
            if (reservationVO.getRoomIds() != null) {
                reservation.setRooms(reservationVO.getRoomIds().stream().map(Room::new).collect(Collectors.toList()));
            } else {
                reservation.setRooms(new ArrayList<>());
            }
            if (reservationVO.getStatus() != null) {
                reservation.setStatus(reservationVO.getStatus());
            }
            reservation.setNumberOfGuests(reservationVO.getNumberOfGuests());
            reservation.setCheckIn(reservationVO.getCheckInDate());
            reservation.setCheckOut(reservationVO.getCheckOutDate());
            reservation.setTotalCost(reservationVO.getTotalCost());
            reservation.setAdditionalInfo(reservationVO.getAdditionalInfo());
            reservation.setAdditionalRequests(reservationVO.getAdditionalRequests());
            reservation.setReservationCode(reservationVO.getReservationCode());
            reservation.setReservationCompany(reservationVO.getReservationCompany());

            return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(reservation));
        } catch (InsufficientAuthenticationException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the reservation.");
        }
    }


}

