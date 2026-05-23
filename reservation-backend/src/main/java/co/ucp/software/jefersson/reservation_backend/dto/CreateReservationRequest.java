package co.ucp.software.jefersson.reservation_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * API request to create a new reservation.
 *
 * @param customerName name of the customer
 * @param date         reservation date
 * @param time         reservation time
 */
public record CreateReservationRequest(
        String customerName,
        LocalDate date,
        LocalTime time
) {}
