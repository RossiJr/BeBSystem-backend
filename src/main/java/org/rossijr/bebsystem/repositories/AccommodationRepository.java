package org.rossijr.bebsystem.repositories;

import org.rossijr.bebsystem.models.Accommodation;
import org.rossijr.bebsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, UUID> {
    Accommodation findByNameAndCreatedBy(String name, User createdBy);
    List<Accommodation> findByCreatedBy(User createdBy);
}
