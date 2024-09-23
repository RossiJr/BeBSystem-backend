package org.rossijr.bebsystem.repositories;


import org.rossijr.bebsystem.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query(value = "SELECT r.* FROM tb_reservation r INNER JOIN tb_mm_reservation_room mrr on mrr.reservation_id = r.id INNER JOIN tb_room roo on mrr.room_id = roo.id INNER JOIN tb_accommodation a on roo.accommodation_id = a.id where a.created_by = ?1", nativeQuery = true)
    List<Reservation> findByUserId(UUID userId);

    @Query(value = "SELECT r.* FROM tb_reservation r INNER JOIN tb_mm_reservation_room mrr on mrr.reservation_id = r.id INNER JOIN tb_room roo on mrr.room_id = roo.id INNER JOIN tb_accommodation a on roo.accommodation_id = a.id where a.created_by = ?1 and ((r.check_in between ?2 and ?3) or (r.check_out between ?2 and ?3))", nativeQuery = true)
    List<Reservation> findByUserIdAndTimeFrame(UUID userId, Calendar frame1, Calendar frame2);
}
