package org.rossijr.bebsystem.VOs.v1;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.rossijr.bebsystem.enums.AccommodationStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class AccommodationVO implements Serializable {
    private UUID id;
    private String name;
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "rooms")
    private List<RoomVO> rooms;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "reservations")
    private List<UUID> reservationsIds;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private AccommodationStatus status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "created_by")
    private UUID createdBy;

    public AccommodationVO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoomVO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomVO> rooms) {
        this.rooms = rooms;
    }

    public List<UUID> getReservationsIds() {
        return reservationsIds;
    }

    public void setReservationsIds(List<UUID> reservationsIds) {
        this.reservationsIds = reservationsIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }
}
