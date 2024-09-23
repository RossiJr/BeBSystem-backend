package org.rossijr.bebsystem.VOs.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.rossijr.bebsystem.enums.RoomCategory;
import org.rossijr.bebsystem.enums.RoomStatus;

import java.util.UUID;

public class RoomVO {
    private UUID id;
    private String name;
    @JsonProperty("accommodation_id")
    private UUID accommodationId;
    private Integer capacity;
    private RoomCategory category;
    private RoomStatus status;


    public RoomVO() {
    }

    public RoomVO(UUID id, String name, UUID accommodationId, Integer capacity, RoomCategory category, RoomStatus status) {
        this.id = id;
        this.name = name;
        this.accommodationId = accommodationId;
        this.capacity = capacity;
        this.category = category;
        this.status = status;
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

    public UUID getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(UUID accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
