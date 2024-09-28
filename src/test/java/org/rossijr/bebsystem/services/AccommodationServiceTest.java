package org.rossijr.bebsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.repositories.AccommodationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationService accommodationService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateAccommodation_InvalidNecessaryFields() {
        //Tests for null accommodation
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(null));
        Accommodation accommodation = new Accommodation();

        //Tests for null createdBy (user)
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(accommodation));
        accommodation.setCreatedBy(new User());

        //Tests for null name
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(accommodation));
        accommodation.setName("");

        //Tests for empty name
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(accommodation));
        accommodation.setName("  ");

        //Tests for blank name
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(accommodation));
        accommodation.setName("name");

        //Tests for name already in use
        when(accommodationRepository.findByNameAndCreatedBy(anyString(), any(User.class))).thenReturn(new Accommodation());
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(accommodation));

        //Tests for invalid email
        accommodation.setEmail("email");
        assertThrows(IllegalArgumentException.class, () -> accommodationService.createAccommodation(accommodation));
    }

    @Test
    void testCreateAccommodation_ValidAccommodation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setName("name    ");
        accommodation.setEmail("teste@teste.com    ");
        accommodation.setCreatedBy(new User());
        when(accommodationRepository.findByNameAndCreatedBy(anyString(), any(User.class))).thenReturn(null);
        when(accommodationRepository.save(any(Accommodation.class))).thenAnswer(i -> i.getArguments()[0]);
        Accommodation createdAccommodation = accommodationService.createAccommodation(accommodation);

        assertNotNull(createdAccommodation);
        assertEquals(createdAccommodation.getName() , "name");
        assertEquals(createdAccommodation.getEmail() , "teste@teste.com");
        assertNotNull(createdAccommodation.getCreatedBy());
        assertNotNull(createdAccommodation.getStatus());
        assertNotNull(createdAccommodation.getCreatedAt());
    }
}
