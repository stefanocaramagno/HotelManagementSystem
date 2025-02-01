package hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;

@Entity
@Table(name = "PAID_BOOKINGS")
public class PaidBooking extends Booking {

    // Costruttore Predefinito
    public PaidBooking() {
        super();
    }

    // Costruttore con Parametri
    public PaidBooking(RoomCategory roomCategory, Customer customer, LocalDate startDate, LocalDate endDate, String status, double totalPrice) {
        super(roomCategory, customer, startDate, endDate, status, totalPrice);
    }

    // Getters personalizzati per il JSON
    @Transient
    public String getCustomerName() {
        return getCustomer() != null ? getCustomer().getFullName() : null;
    }

    @Transient
    public String getRoomType() {
        return getRoomCategory() != null ? getRoomCategory().getRoomType() : null;
    }
}
