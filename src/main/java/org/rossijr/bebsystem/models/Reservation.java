package org.rossijr.bebsystem.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.rossijr.bebsystem.enums.ReservationStatus;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_reservation")
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "ID", updatable = false, nullable = false, unique = true)
    private UUID id;

    @JoinColumn(name = "COSTUMER_ID")
    @ManyToOne
    @JsonBackReference
    private Customer costumer;

    @ManyToMany
    @JoinTable(
            name = "tb_mm_reservation_room",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;

    @Column(name = "STATUS", columnDefinition = "TEXT")
    private ReservationStatus status;

    @Column(name = "NUMBER_OF_GUESTS", columnDefinition = "INTEGER")
    private Integer numberOfGuests;

    @Column(name = "CHECK_IN", columnDefinition = "TIMESTAMP")
    private Calendar checkIn;

    @Column(name = "CHECK_OUT", columnDefinition = "TIMESTAMP")
    private Calendar checkOut;

    @Column(name = "TOTAL_COST", columnDefinition = "DOUBLE")
    private Double totalCost;

    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP")
    private Calendar createdAt;

    @Column(name = "PAYED_AT", columnDefinition = "TIMESTAMP")
    private Calendar payedAt;

    @Column(name = "ADDITIONAL_INFO", columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "ADDITIONAL_REQUESTS", columnDefinition = "TEXT")
    private String additionalRequests;


    public Reservation() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCostumer() {
        return costumer;
    }

    public void setCostumer(Customer costumer) {
        this.costumer = costumer;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
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

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
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
}
