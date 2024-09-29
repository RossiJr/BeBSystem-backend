package org.rossijr.bebsystem.services;

import org.rossijr.bebsystem.Utils;
import org.rossijr.bebsystem.enums.ReservationStatus;
import org.rossijr.bebsystem.models.Reservation;
import org.rossijr.bebsystem.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getReservationsByUserId(UUID userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByUserIdAndDays(UUID userId, Integer days) {
        Calendar now = Calendar.getInstance();
        Calendar timeFrame = Calendar.getInstance();
        timeFrame.add(Calendar.DAY_OF_MONTH, days);
        return reservationRepository.findByUserIdAndTimeFrame(userId, now, timeFrame);
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }
        if (reservation.getCustomer() == null || reservation.getCustomer().getId() == null){
            throw new IllegalArgumentException("Customer must be provided.");
        }
        if (reservation.getRooms() == null || reservation.getRooms().isEmpty()) {
            throw new IllegalArgumentException("At least one room must be selected.");
        }
        if (reservation.getCheckIn() == null || reservation.getCheckOut() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must be provided.");
        }
        if(Utils.isSameDate(reservation.getCheckIn(), reservation.getCheckOut())){
            throw new IllegalArgumentException("Check-out date must be at 1 least one day after check-in date.");
        }
        if (reservation.getCheckIn().after(reservation.getCheckOut())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }

        Reservation obj = new Reservation();

        obj.setRooms(reservation.getRooms());
        obj.setCustomer(reservation.getCustomer());
        obj.setStatus(reservation.getStatus() == null ? ReservationStatus.PENDING : reservation.getStatus());
        obj.setNumberOfGuests(reservation.getNumberOfGuests());
        obj.setCheckIn(reservation.getCheckIn());
        obj.setCheckOut(reservation.getCheckOut());
        obj.setTotalCost(reservation.getTotalCost());
        obj.setCreatedAt(Calendar.getInstance());
        obj.setPayedAt(reservation.getPayedAt());
        obj.setAdditionalInfo(reservation.getAdditionalInfo());
        obj.setAdditionalRequests(reservation.getAdditionalRequests());
        obj.setReservationCode(reservation.getReservationCode());
        obj.setReservationCompany(reservation.getReservationCompany());

        return reservationRepository.save(obj);
    }
}
