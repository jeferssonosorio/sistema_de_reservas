package co.ucp.software.jefersson.reservation_backend.repository;

import co.ucp.software.jefersson.reservation_backend.entity.ReservationEntity;
import co.ucp.software.jefersson.reservation_backend.entity.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for {@link ReservationEntity}.
 */
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    /**
     * Checks whether a non-cancelled reservation exists for the given date and time.
     *
     * @param date   reservation date
     * @param time   reservation time
     * @param status status to exclude (typically {@link ReservationStatus#CANCELLED})
     * @return {@code true} if another reservation occupies the slot
     */
    boolean existsByDateAndTimeAndStatusNot(LocalDate date, LocalTime time, ReservationStatus status);
}
