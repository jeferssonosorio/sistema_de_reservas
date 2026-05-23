package co.ucp.software.jefersson.reservation_backend.service;

import co.ucp.software.jefersson.reservation_backend.dto.CreateReservationRequest;
import co.ucp.software.jefersson.reservation_backend.dto.ReservationResponse;
import co.ucp.software.jefersson.reservation_backend.entity.ReservationEntity;
import co.ucp.software.jefersson.reservation_backend.entity.ReservationStatus;
import co.ucp.software.jefersson.reservation_backend.exception.ReservationAlreadyCancelledException;
import co.ucp.software.jefersson.reservation_backend.exception.ReservationNotFoundException;
import co.ucp.software.jefersson.reservation_backend.exception.ReservationSlotAlreadyTakenException;
import co.ucp.software.jefersson.reservation_backend.mapper.ReservationMapper;
import co.ucp.software.jefersson.reservation_backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for reservation lifecycle operations.
 */
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    /**
     * Creates the service with required collaborators.
     *
     * @param reservationRepository persistence access for reservations
     * @param reservationMapper     entity and DTO mapper
     */
    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    /**
     * Creates a reservation only when no other non-cancelled reservation exists
     * for the same date and time.
     *
     * @param request reservation data
     * @return the created reservation
     * @throws ReservationSlotAlreadyTakenException if the slot is already booked
     */
    public ReservationResponse createReservation(CreateReservationRequest request) {
        if (reservationRepository.existsByDateAndTimeAndStatusNot(
                request.date(), request.time(), ReservationStatus.CANCELLED)) {
            throw new ReservationSlotAlreadyTakenException(request.date(), request.time());
        }

        ReservationEntity entity = reservationMapper.toEntity(request);
        entity.setStatus(ReservationStatus.ACTIVE);
        ReservationEntity saved = reservationRepository.save(entity);
        return reservationMapper.toResponse(saved);
    }

    /**
     * Cancels a reservation by id.
     *
     * @param id reservation identifier
     * @return the cancelled reservation
     * @throws ReservationNotFoundException         if the id does not exist
     * @throws ReservationAlreadyCancelledException if the reservation is already cancelled
     */
    public ReservationResponse cancelReservation(Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (entity.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationAlreadyCancelledException(id);
        }

        reservationMapper.updateStatus(entity, ReservationStatus.CANCELLED);
        ReservationEntity saved = reservationRepository.save(entity);
        return reservationMapper.toResponse(saved);
    }
}
