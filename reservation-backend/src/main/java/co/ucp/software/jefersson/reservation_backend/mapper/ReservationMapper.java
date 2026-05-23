package co.ucp.software.jefersson.reservation_backend.mapper;

import co.ucp.software.jefersson.reservation_backend.dto.CreateReservationRequest;
import co.ucp.software.jefersson.reservation_backend.dto.ReservationResponse;
import co.ucp.software.jefersson.reservation_backend.entity.ReservationEntity;
import co.ucp.software.jefersson.reservation_backend.entity.ReservationStatus;
import org.springframework.stereotype.Component;

/**
 * Maps between reservation entities and API DTOs.
 */
@Component
public class ReservationMapper {

    /**
     * Builds a new entity from a create request (status is set by the service).
     *
     * @param request create reservation payload
     * @return a new {@link ReservationEntity} without id
     */
    public ReservationEntity toEntity(CreateReservationRequest request) {
        ReservationEntity entity = new ReservationEntity();
        entity.setCustomerName(request.customerName());
        entity.setDate(request.date());
        entity.setTime(request.time());
        return entity;
    }

    /**
     * Converts a persisted entity to an API response.
     *
     * @param entity persisted reservation
     * @return API response DTO
     */
    public ReservationResponse toResponse(ReservationEntity entity) {
        return new ReservationResponse(
                entity.getId(),
                entity.getCustomerName(),
                entity.getDate(),
                entity.getTime(),
                entity.getStatus()
        );
    }

    /**
     * Applies {@link ReservationStatus} on an existing entity.
     *
     * @param entity entity to update
     * @param status new status
     */
    public void updateStatus(ReservationEntity entity, ReservationStatus status) {
        entity.setStatus(status);
    }
}
