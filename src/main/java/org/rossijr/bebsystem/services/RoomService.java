package org.rossijr.bebsystem.services;

import org.rossijr.bebsystem.VOs.v1.RoomVO;
import org.rossijr.bebsystem.enums.RoomCategory;
import org.rossijr.bebsystem.enums.RoomStatus;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.Room;
import org.rossijr.bebsystem.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AccommodationService accommodationService;

    public Room createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (room.getName() == null || room.getName().isBlank()) {
            throw new IllegalArgumentException("Room name cannot be null or empty");
        }
        if (room.getAccommodation() == null || room.getAccommodation().getId() == null) {
            throw new IllegalArgumentException("Accommodation cannot be null");
        }
        if (accommodationService.getAccommodationById(room.getAccommodation().getId()) == null) {
            throw new IllegalArgumentException("Accommodation not found");
        }
        if (roomRepository.findByNameAndAccommodation_Id(room.getName(), room.getAccommodation().getId()) != null) {
            throw new IllegalArgumentException("Room {" + room.getName() + "} already exists in this accommodation");
        }

        Room obj = new Room();
        obj.setName(room.getName());
        obj.setAccommodation(new Accommodation(room.getAccommodation().getId()));
        obj.setCapacity(room.getCapacity() != null ? room.getCapacity() : 1);
        obj.setCategory(room.getCategory() != null ? room.getCategory() : RoomCategory.NON_DEFINED);
        obj.setStatus(RoomStatus.CREATED);
        obj.setCreatedAt(Calendar.getInstance());

        return roomRepository.save(obj);
    }

    public List<RoomVO> getRooms(){
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(room -> new RoomVO(room.getId(), room.getName(), room.getAccommodation().getId(), room.getCapacity(), room.getCategory(), room.getStatus())).collect(Collectors.toList());
    }
}
