package org.rossijr.bebsystem.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rossijr.bebsystem.enums.ReservationStatus;
import org.rossijr.bebsystem.models.Customer;
import org.rossijr.bebsystem.models.Reservation;
import org.rossijr.bebsystem.models.Room;
import org.rossijr.bebsystem.repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;



    @Test
    void createReservation_InvalidNecessaryFields() {

        //Tests for reservation
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(null));
        Reservation reservation = new Reservation();

        //Tests for null customer
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
        reservation.setCustomer(new Customer(UUID.randomUUID()));

        // Tests for empty rooms
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
        reservation.setRooms(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));

        //Tests for null checkIn
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
        reservation.setCheckIn(Calendar.getInstance());

        //Tests for null checkOut
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
        Calendar checkOut = Calendar.getInstance();
        reservation.setCheckOut(checkOut);

        //Tests for checkOut same as checkIn
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));
        checkOut.add(Calendar.DAY_OF_MONTH, -2);
        reservation.setCheckOut(checkOut);

        //Tests for checkOut before checkIn
        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(reservation));

    }

    @Test
    void createReservation_ValidReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        Reservation reservation = new Reservation();
        reservation.setCustomer(new Customer(UUID.randomUUID()));
        reservation.setRooms(new ArrayList<>());
        reservation.getRooms().add(new Room(UUID.randomUUID()));
        reservation.setCheckIn(Calendar.getInstance());
        Calendar checkOut = Calendar.getInstance();
        checkOut.add(Calendar.DAY_OF_MONTH, 2);
        reservation.setCheckOut(checkOut);

        Reservation createdReservation1 = reservationService.createReservation(reservation);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        Reservation createdReservation2 = reservationService.createReservation(reservation);

        assertNotNull(createdReservation1);
        assertEquals(createdReservation1.getStatus(), ReservationStatus.PENDING);
        assertEquals(createdReservation2.getStatus(), ReservationStatus.CONFIRMED);
        assertNotNull(createdReservation1.getCreatedAt());
    }


}
