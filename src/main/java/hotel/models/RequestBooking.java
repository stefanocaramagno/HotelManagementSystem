package hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "REQUEST_BOOKINGS")
public class RequestBooking extends Booking {

    // Costruttore Predefinito
    public RequestBooking() {
        super();
    }

    // Costruttore con Parametri
    public RequestBooking(RoomCategory roomCategory, Customer customer, LocalDate startDate, LocalDate endDate, String status, double totalPrice) {
        super(roomCategory, customer, startDate, endDate, status, totalPrice);
    }
}
