package org.rossijr.bebsystem.VOs.v1.reservations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.rossijr.bebsystem.enums.ReservationCompany;
import org.rossijr.bebsystem.enums.ReservationStatus;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public class ReservationChartVO {
    private UUID id;
    private Map<UUID, String> rooms;
    private ReservationStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @JsonProperty("check_in")
    private Calendar checkIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @JsonProperty("check_out")
    private Calendar checkOut;
    private ReservationCompany company;
    @JsonProperty("reservation_code")
    private String reservationCode;

    public ReservationChartVO(UUID id, Map<UUID, String> rooms, ReservationStatus status, Calendar checkIn, Calendar checkOut, ReservationCompany company, String reservationCode) {
        this.id = id;
        this.rooms = rooms;
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.company = company;
        this.reservationCode = reservationCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<UUID, String> getRooms() {
        return rooms;
    }

    public void setRooms(Map<UUID, String> rooms) {
        this.rooms = rooms;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Calendar getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Calendar checkIn) {
        this.checkIn = checkIn;
    }

    public Calendar getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Calendar checkOut) {
        this.checkOut = checkOut;
    }

    public ReservationCompany getCompany() {
        return company;
    }

    public void setCompany(ReservationCompany company) {
        this.company = company;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }
}
