package hotel.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ROOMS")
public class Room {

    // Attributi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRoom;

    @ManyToOne
    @JoinColumn(name = "id_room_category", nullable = false)
    private RoomCategory roomCategory;

    @Column(name = "cleaning_status", nullable = false)
    private String cleaningStatus = "Dirty";

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Costruttore Predefinito (necessario per JPA)
    public Room() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Costruttore con Parametri
    public Room(int idRoom, RoomCategory roomCategory, String cleaningStatus) {
        this.idRoom = idRoom;
        this.roomCategory = roomCategory;
        this.cleaningStatus = cleaningStatus;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public RoomCategory getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }

    public String getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(String cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }

    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }

}
