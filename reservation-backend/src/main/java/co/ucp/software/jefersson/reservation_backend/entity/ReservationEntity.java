package co.ucp.software.jefersson.reservation_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * JPA persistence model for a reservation.
 */
@Entity
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private LocalDate date;
    private LocalTime time;


    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Long getId() {
        return id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public ReservationStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ReservationEntity() {
        this.status = ReservationStatus.ACTIVE;
    }

    /**
     * Constructor for a reservation.
     * @param id the reservation identifier
     * @param customerName the name of the customer
     * @param date the date of the reservation
     * @param time the time of the reservation
     * @param status the status of the reservation
     */
    public ReservationEntity(Long id, String customerName, LocalDate date, LocalTime time, ReservationStatus status) {
        this.id = id;
        this.customerName = customerName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

}