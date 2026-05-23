package co.ucp.software.jefersson.reservation_backend.exception;

import java.time.Instant;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Maps domain exceptions to consistent HTTP error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles missing reservation errors.
     *
     * @param ex not found exception
     * @return 404 response body
     */
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ReservationNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles duplicate slot conflicts.
     *
     * @param ex slot already taken exception
     * @return 409 response body
     */
    @ExceptionHandler(ReservationSlotAlreadyTakenException.class)
    public ResponseEntity<Map<String, Object>> handleSlotTaken(ReservationSlotAlreadyTakenException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Handles invalid cancel operations.
     *
     * @param ex already cancelled exception
     * @return 409 response body
     */
    @ExceptionHandler(ReservationAlreadyCancelledException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyCancelled(
            ReservationAlreadyCancelledException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        log.warn("Business rule violation: {}", message);
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        ));
    }
}
