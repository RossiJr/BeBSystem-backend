package org.rossijr.bebsystem.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rossijr.bebsystem.enums.RoomCategory;
import org.rossijr.bebsystem.enums.RoomStatus;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.Room;
import org.rossijr.bebsystem.repositories.RoomRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AccommodationService accommodationService;

    @InjectMocks
    private RoomService roomService;


    @Test
    void createRoom_InvalidNecessaryFields() {
        UUID id = UUID.randomUUID();
        when(accommodationService.getAccommodationById(id)).thenReturn(null);
        when(roomRepository.findByNameAndAccommodation_Id("Room 1", id)).thenReturn(new Room());

        // Tests for room creation - null room
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(null));
        Room room = new Room();

        // Tests for room creation - null or blank name
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));
        room.setName(" ");
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));
        room.setName("Room 1");

        // Tests for room creation - null or no ID accommodation
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));
        room.setAccommodation(new Accommodation());
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));
        room.getAccommodation().setId(id);

        // Tests for room creation - accommodation not found
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));
        when(accommodationService.getAccommodationById(id)).thenReturn(new Accommodation(id));

        // Tests for room creation - room already exists
        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));

    }

    @Test
    void createRoom_ValidRoom() {
        UUID id = UUID.randomUUID();
        when(accommodationService.getAccommodationById(id)).thenReturn(new Accommodation(id));
        when(roomRepository.findByNameAndAccommodation_Id("Room 1", id)).thenReturn(null);

        Room room = new Room();
        room.setName("Room 1");
        room.setAccommodation(new Accommodation(id));

        when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArguments()[0]);

        Room roomTest1 = roomService.createRoom(room);
        assertEquals(room.getName(), roomTest1.getName());
        assertEquals(roomTest1.getCapacity(), 1);
        assertEquals(roomTest1.getCategory(), RoomCategory.NON_DEFINED);
        assertEquals(roomTest1.getStatus(), RoomStatus.CREATED);
        assertNotNull(roomTest1.getCreatedAt());

        room.setCapacity(2);
        room.setCategory(RoomCategory.SUITE);

        Room roomTest2 = roomService.createRoom(room);
        assertEquals(room.getCapacity(), roomTest2.getCapacity());
        assertEquals(room.getCategory(), roomTest2.getCategory());
    }


}
