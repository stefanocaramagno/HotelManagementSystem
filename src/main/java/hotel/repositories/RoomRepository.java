package hotel.repositories;

import hotel.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    /**
     * Questa repository viene utilizzata per gestire l'accesso ai dati
     * relativi al modello Room.
     * Extendendo JpaRepository, forniamo un insieme di metodi 
     * predefiniti (ad esempio, save, findById, findAll, delete) per operare
     * sulla tabella 'ROOMS' nel database. 
    */

    // Metodo per Trovare le Stanze con uno Specifico Stato di Pulizia
    @Query("SELECT r " +
           "FROM Room r " +    
           "WHERE r.cleaningStatus = 'Dirty'")
    List<Room> findDirtyRooms();

}
