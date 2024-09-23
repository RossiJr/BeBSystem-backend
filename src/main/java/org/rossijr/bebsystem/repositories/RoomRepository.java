package org.rossijr.bebsystem.repositories;

import org.rossijr.bebsystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Room findByNameAndAccommodation_Id(String name, UUID accommodationId);
}
