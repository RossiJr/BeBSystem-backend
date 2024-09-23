package org.rossijr.bebsystem.VOs.v1.reservations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.rossijr.bebsystem.enums.ReservationCompany;
import org.rossijr.bebsystem.enums.ReservationStatus;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class ReservationVO {
    private UUID id;
    @JsonProperty("customer_id")
    private UUID customerId;
    @JsonProperty("room_ids")
    private List<UUID> roomIds;
    private ReservationStatus status;
    @JsonProperty("number_of_guests")
    private Integer numberOfGuests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("check_in_date")
    private Calendar checkInDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("check_out_date")
    private Calendar checkOutDate;
    @JsonProperty("total_cost")
    private Double totalCost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("reservation_date")
    private Calendar reservationDate;
    @JsonProperty("payed_at")
    private Calendar payedAt;
    @JsonProperty("additional_info")
    private String additionalInfo;
    @JsonProperty("additional_requests")
    private String additionalRequests;
    @JsonProperty("reservation_code")
    private String reservationCode;
    @JsonProperty("reservation_company")
    private ReservationCompany reservationCompany;

    public ReservationVO() {
    }

    public ReservationVO(UUID id, UUID customerId, List<UUID> roomIds, ReservationStatus status, Integer numberOfGuests, Calendar checkInDate, Calendar checkOutDate, Double totalCost, Calendar reservationDate, Calendar payedAt, String additionalInfo, String additionalRequests, String reservationCode, ReservationCompany reservationCompany) {
        this.id = id;
        this.customerId = customerId;
        this.roomIds = roomIds;
        this.status = status;
        this.numberOfGuests = numberOfGuests;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
        this.reservationDate = reservationDate;
        this.payedAt = payedAt;
        this.additionalInfo = additionalInfo;
        this.additionalRequests = additionalRequests;
        this.reservationCode = reservationCode;
        this.reservationCompany = reservationCompany;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<UUID> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<UUID> roomIds) {
        this.roomIds = roomIds;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Calendar getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Calendar checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Calendar getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Calendar checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Calendar getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Calendar reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Calendar getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(Calendar payedAt) {
        this.payedAt = payedAt;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalRequests() {
        return additionalRequests;
    }

    public void setAdditionalRequests(String additionalRequests) {
        this.additionalRequests = additionalRequests;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public ReservationCompany getReservationCompany() {
        return reservationCompany;
    }

    public void setReservationCompany(ReservationCompany reservationCompany) {
        this.reservationCompany = reservationCompany;
    }
}
