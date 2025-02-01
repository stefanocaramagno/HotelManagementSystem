/* 
package hotel.controllers;

import hotel.models.Room;
import hotel.models.CleaningStaff;
import hotel.services.CleaningTaskService;
import hotel.services.CleaningStaffService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CleaningTaskControllerTest {

    // Attributi
    @Mock
    private CleaningTaskService cleaningTaskService;

    @Mock
    private CleaningStaffService cleaningStaffService;

    @InjectMocks
    private CleaningTaskController cleaningTaskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // **CASO D'USO N°11 (UC11): ASSEGNA ATTIVITÀ DEL PERSONALE**

    // Test dell'Endpoint per Visualizzare la Lista delle Stanze Sporche
    @Test
    public void testGetDirtyRooms() {
        List<Room> mockDirtyRooms = Collections.singletonList(new Room());
        when(cleaningTaskService.getDirtyRooms()).thenReturn(mockDirtyRooms);

        ResponseEntity<?> response = cleaningTaskController.getDirtyRooms();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockDirtyRooms, response.getBody());

        verify(cleaningTaskService, times(1)).getDirtyRooms();
    }

    // Test dell'Endpoint per Visualizzare la Lista del Cleaning Staff
    @Test
    public void testGetCleaningStaff() {
        List<CleaningStaff> mockStaff = Collections.singletonList(new CleaningStaff());
        when(cleaningStaffService.getAllCleaningStaff()).thenReturn(mockStaff);

        ResponseEntity<?> response = cleaningTaskController.getCleaningStaff();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockStaff, response.getBody());

        verify(cleaningStaffService, times(1)).getAllCleaningStaff();
    }

    // Test dell'Endpoint per Assegnare un Task di Pulizia
    @Test
    public void testAssignCleaningTask() {
        when(cleaningTaskService.assignCleaningTask(anyInt(), anyInt())).thenReturn("Task assigned successfully.");

        ResponseEntity<String> response = cleaningTaskController.assignCleaningTask(1, 101);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Task assigned successfully.", response.getBody());

        verify(cleaningTaskService, times(1)).assignCleaningTask(1, 101);
    }

    // **CASO D'USO N°12 (UC12): GESTISCI STATO DELLE CAMERE**

    // Test dell'Endpoint per Visualizzare la Lista dei Task di Pulizia
    @Test
    public void testGetRoomsPendingClean() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<Room> mockPendingRooms = Collections.singletonList(new Room());
        when(cleaningTaskService.getRoomsPendingClean(anyString())).thenReturn(mockPendingRooms);

        ResponseEntity<?> response = cleaningTaskController.getRoomsPendingClean();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPendingRooms, response.getBody());

        verify(cleaningTaskService, times(1)).getRoomsPendingClean(anyString());
    }

    // Test dell'Endpoint per Gestire l'assegnazione di un Task di Pulizia
    @Test
    public void testManageCleaningTask() {
        when(cleaningTaskService.manageCleaningTask(anyInt(), anyString())).thenReturn("Room status updated successfully.");

        ResponseEntity<String> response = cleaningTaskController.manageCleaningTask(101, "Cleaned");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Room status updated successfully.", response.getBody());

        verify(cleaningTaskService, times(1)).manageCleaningTask(101, "Cleaned");
    }
}
*/