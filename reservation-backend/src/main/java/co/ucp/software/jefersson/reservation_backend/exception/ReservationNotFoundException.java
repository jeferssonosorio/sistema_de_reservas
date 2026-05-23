package co.ucp.software.jefersson.reservation_backend.exception;

/**
 * Thrown when a reservation id does not exist.
 */
public class ReservationNotFoundException extends RuntimeException {

    /**
     * Creates the exception for the given id.
     *
     * @param id reservation identifier that was not found
     */
    public ReservationNotFoundException(Long id) {
        super("Reservation not found with id: " + id);
    }
}
