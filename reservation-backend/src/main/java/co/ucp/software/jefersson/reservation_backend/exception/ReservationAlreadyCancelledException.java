package co.ucp.software.jefersson.reservation_backend.exception;

/**
 * Thrown when attempting to cancel a reservation that is already cancelled.
 */
public class ReservationAlreadyCancelledException extends RuntimeException {

    /**
     * Creates the exception for the given id.
     *
     * @param id reservation identifier
     */
    public ReservationAlreadyCancelledException(Long id) {
        super("Reservation with id %d is already cancelled".formatted(id));
    }
}
