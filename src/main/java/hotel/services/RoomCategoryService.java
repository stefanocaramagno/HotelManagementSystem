package hotel.services;

import hotel.models.RoomCategory;
import hotel.repositories.PaidBookingRepository;
import hotel.repositories.RequestBookingRepository;
import hotel.repositories.RoomCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RoomCategoryService {

    // Attributi
    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private RequestBookingRepository requestBookingRepository;

    @Autowired
    private PaidBookingRepository paidBookingRepository;

    // **CASO D'USO N°1 (UC1): EFFETTUA RICHIESTA DI PRENOTAZIONE**
    
    // Metodo per Calcolare il Numero di Stanza Disponibili per un determinato Tipo di Stanza e un Intervallo di Date
    public int getAvailableRoomsFromRequestBooking(String roomType, String startDate, String endDate) {
        RoomCategory roomCategory = roomCategoryRepository.findByRoomType(roomType);
        if (roomCategory == null) {
            throw new RuntimeException("Room type not found: " + roomType);
        }

        long requestBookedCount = requestBookingRepository.countRequestBookedRooms(roomCategory.getIdRoomCategory(),LocalDate.parse(startDate), LocalDate.parse(endDate));

        return roomCategory.getRoomAmount() - (int) requestBookedCount;
    }

    // Metodo per Verificare la Disponibilità da RequestBooking
    public boolean isRoomAvailableFromRequestBooking(String roomType, String startDate, String endDate) {
        return getAvailableRoomsFromRequestBooking(roomType, startDate, endDate) > 0;
    }

    // **CASO D'USO N°5 (UC5): RICHIEDI MODIFICA DELLA PRENOTAZIONE**

    // Metodo per Calcolare il Numero di Stanze Disponibili per un determinato Tipo di Stanza e un Intervallo di Date 
    public int getAvailableRoomsFromPaidBooking(String roomType, String startDate, String endDate) {
        RoomCategory roomCategory = roomCategoryRepository.findByRoomType(roomType);
        if (roomCategory == null) {
            throw new RuntimeException("Room type not found: " + roomType);
        }

        long paidBookedCount = paidBookingRepository.countPaidBookedRooms(roomCategory.getIdRoomCategory(), LocalDate.parse(startDate), LocalDate.parse(endDate));

        return roomCategory.getRoomAmount() - (int) paidBookedCount;
    }

    // Metodo per Verificare la Disponibilità da PaidBooking
    public boolean isRoomAvailableFromPaidBooking(String roomType, String startDate, String endDate) {
        return getAvailableRoomsFromPaidBooking(roomType, startDate, endDate) > 0;
    }
 
}
