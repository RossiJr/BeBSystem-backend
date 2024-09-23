package org.rossijr.bebsystem.services;

import org.rossijr.bebsystem.Utils;
import org.rossijr.bebsystem.VOs.v1.AccommodationVO;
import org.rossijr.bebsystem.VOs.v1.RoomVO;
import org.rossijr.bebsystem.enums.AccommodationStatus;
import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.Reservation;
import org.rossijr.bebsystem.models.User;
import org.rossijr.bebsystem.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    private boolean isUniqueName(String name, User user) {
        return accommodationRepository.findByNameAndCreatedBy(name, user) == null;
    }

    public Accommodation getAccommodationById(UUID id){
        return accommodationRepository.findById(id).orElse(null);
    }

    private AccommodationVO convertToVO(Accommodation accommodation){
        AccommodationVO vo = new AccommodationVO();
        vo.setId(accommodation.getId());
        vo.setName(accommodation.getName());
        vo.setRooms(accommodation.getRooms() == null ? new ArrayList<>() : accommodation.getRooms().stream().map(r -> new RoomVO(r.getId(), r.getName(), r.getAccommodation().getId(), r.getCapacity(), r.getCategory(), r.getStatus())).collect(Collectors.toList()));
        vo.setReservationsIds(accommodation.getReservations() == null ? new ArrayList<>() : accommodation.getReservations().stream().map(Reservation::getId).collect(Collectors.toList()));
        vo.setEmail(accommodation.getEmail());
        vo.setPhoneNumber(accommodation.getPhoneNumber());
        vo.setStatus(accommodation.getStatus());
        return vo;
    }

    public Accommodation createAccommodation(Accommodation accommodation) {
        if (accommodation == null) {
            throw new IllegalArgumentException("The accommodation cannot be null");
        }
        if (accommodation.getCreatedBy() == null){
            throw new IllegalArgumentException("The user who created the accommodation cannot be null");
        }
        if (accommodation.getName() == null || accommodation.getName().isEmpty()) {
            throw new IllegalArgumentException("The accommodation name cannot be null or empty");
        }
        if (!isUniqueName(accommodation.getName().trim(), accommodation.getCreatedBy())) {
            throw new IllegalArgumentException("This accommodation name is already in use");
        }
        if(accommodation.getEmail() != null && !accommodation.getEmail().isEmpty() && !Utils.isValidEmail(accommodation.getEmail())){
            throw new IllegalArgumentException("The email needs to be valid");
        }

        Accommodation obj = new Accommodation();
        obj.setName(accommodation.getName().trim());
        obj.setEmail(accommodation.getEmail());
        obj.setPhoneNumber(accommodation.getPhoneNumber() == null ? null : Utils.cleanPhoneNumber(accommodation.getPhoneNumber()));
        obj.setStatus(AccommodationStatus.CREATED);
        obj.setCreatedAt(Calendar.getInstance());
        obj.setCreatedBy(accommodation.getCreatedBy());

        return accommodationRepository.save(obj);
    }

    public List<AccommodationVO> getAccommodations(User user){
        List<Accommodation> accommodations = accommodationRepository.findByCreatedBy(user);

        return accommodations.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    public List<AccommodationVO> getAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();

        return accommodations.stream().map(this::convertToVO).collect(Collectors.toList());
    }
}
