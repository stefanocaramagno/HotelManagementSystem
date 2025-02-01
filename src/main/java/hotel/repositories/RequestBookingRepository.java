package hotel.repositories;

import hotel.models.RequestBooking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RequestBookingRepository extends JpaRepository<RequestBooking, Integer> {

    /**
     * Questa repository viene utilizzata per gestire l'accesso ai dati
     * relativi al modello RequestBooking.
     * Extendendo JpaRepository, forniamo un insieme di metodi 
     * predefiniti (ad esempio, save, findById, findAll, delete) per operare
     * sulla tabella 'REQUEST_BOOKINGS' nel database. 
    */

    // Metodo (query) per Contare le Richieste di Prenotazione di una Stanza in un determinato Intervallo di Date
    @Query("SELECT COUNT(requestBooking) " +
           "FROM RequestBooking requestBooking " +
           "WHERE requestBooking.roomCategory.idRoomCategory = :idRoomCategory " +
                "AND requestBooking.startDate <= :endDate AND requestBooking.endDate >= :startDate")
    long countRequestBookedRooms(@Param("idRoomCategory") int roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Metodo (query) per Trovare tutte le Richieste di Prenotazione in Stato di Attesa di Essere Gestita (Approvata / Rifiutata)
    @Query("SELECT requestBooking " +
           "FROM RequestBooking requestBooking " +
           "WHERE requestBooking.status = 'Pending Approval'")
    List<RequestBooking> findPendingRequests();

    // Metodo (query) per Trovare le Richieste di Prenotazione in Stato di Attesa di Pagamento
    @Query("SELECT requestBooking FROM RequestBooking requestBooking " +
           "WHERE requestBooking.customer.idCustomer = :idCustomer " +
              "AND requestBooking.status = 'Approved'")
    List<RequestBooking> findApprovedBookings(@Param("idCustomer") int idCustomer);

    // Metodo (query) per Salvare una Prenotazione nella Tabella delle Prenotazione confermate da un Pagamento 
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO PAID_BOOKINGS (id_room, id_customer, start_date, end_date, status, total_price, created_at) " +
                   "VALUES (?1, ?2, ?3, ?4, ?5, ?6, CURRENT_TIMESTAMP)", nativeQuery = true)
    void savePaidBooking(int idRoom, int idCustomer, LocalDate startDate, LocalDate endDate, String status, Double totalPrice);

}
