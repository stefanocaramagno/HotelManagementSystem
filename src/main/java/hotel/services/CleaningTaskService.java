package hotel.services;

import hotel.models.CleaningTask;
import hotel.models.Room;
import hotel.models.CleaningStaff;
import hotel.repositories.CleaningTaskRepository;
import hotel.repositories.RoomRepository;
import hotel.repositories.CleaningStaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleaningTaskService {

    // Attributi
    @Autowired
    private CleaningTaskRepository cleaningTaskRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CleaningStaffRepository cleaningStaffRepository;

    // **CASO D'USO N°11 (UC11): ASSEGNA ATTIVITÀ DEL PERSONALE **

    // Metodo per Visualizzare la Lista delle Stanze Sporche
    public List<Room> getDirtyRooms() {
        return roomRepository.findDirtyRooms();
    }

    // Metodo per Visualizzare la Lista dei Cleaning Staff
    public List<CleaningStaff> getCleaningStaff() {
        return cleaningStaffRepository.findAll();
    }
    
    // Metodo per Assegnare un Task di Pulizia
    public String assignCleaningTask(int idCleaningStaff, int idRoom) {
        Room room = roomRepository.findById(idRoom).orElseThrow(() -> new IllegalArgumentException("Room not found."));
        CleaningStaff cleaningStaff = cleaningStaffRepository.findById(idCleaningStaff).orElseThrow(() -> new IllegalArgumentException("Staff member not found."));

        if (!"Dirty".equals(room.getCleaningStatus())) {
            return "Room is not dirty.";
        }

        CleaningTask cleaningTask = new CleaningTask();
        cleaningTask.setRoom(room);
        cleaningTask.setCleaningStaff(cleaningStaff);
        cleaningTaskRepository.save(cleaningTask);

        room.setCleaningStatus("Pending Clean");
        roomRepository.save(room);

        return "Task assigned successfully.";
    }

    // **CASO D'USO N°12 (UC12): GESTISCI STATO DELLE CAMERE **

    // Metodo per Visualizzare la Lista dei Task di Pulizia
    public List<Room> getRoomsPendingClean(String cleaningStaffEmail) {
        CleaningStaff cleaningStaff = cleaningStaffRepository.findByEmail(cleaningStaffEmail);
        if (cleaningStaff == null) {
            throw new RuntimeException("Staff not found with email: " + cleaningStaffEmail);
        }

        List<CleaningTask> assignedTasks = cleaningTaskRepository.findCleaningTasks(cleaningStaff.getIdCleaningStaff());
    
        return assignedTasks.stream()
                .map(CleaningTask::getRoom)
                .distinct()
                .toList();
    }
    
    // Metodo per Gestire l'assegnazione di un Task di Pulizia
    public String manageCleaningTask(int idRoom, String newStatus) {
        Room room = roomRepository.findById(idRoom).orElse(null);
        if (room == null) {
            return "Room not found.";
        }

        if (!newStatus.equalsIgnoreCase("Clean") && !newStatus.equalsIgnoreCase("Dirty")) {
            return "Invalid room status. Valid statuses are 'Clean' or 'Dirty'.";
        }
    
        room.setCleaningStatus(newStatus);
        roomRepository.save(room);
    
        return "Room status updated successfully.";
    }

}
