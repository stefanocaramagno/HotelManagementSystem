/* 
package hotel.controllers;

import hotel.services.RoomCategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RoomCategoryControllerTest {

    // Attributi
    @Mock
    private RoomCategoryService roomCategoryService;

    @InjectMocks
    private RoomCategoryController roomCategoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // **CASO D'USO N°1 (UC1): EFFETTUA RICHIESTA DI PRENOTAZIONE**

    // Test dell'Endpoint per Calcolare il Numero di Stanze Disponibili per una Richiesta di Prenotazione
    @Test
    public void testCheckRoomAvailabilityForRequestBooking() {
        when(roomCategoryService.getAvailableRoomsFromRequestBooking(anyString(), anyString(), anyString())).thenReturn(5);

        ResponseEntity<?> response = roomCategoryController.checkRoomAvailabilityforRequestBooking("Single Room", "2024-01-01", "2024-01-10");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Map.of("available", 5), response.getBody());

        verify(roomCategoryService, times(1)).getAvailableRoomsFromRequestBooking("Single Room", "2024-01-01", "2024-01-10");
    }

    // **CASO D'USO N°5 (UC5): RICHIEDI MODIFICA DELLA PRENOTAZIONE**

    // Test dell'Endpoint per Calcolare il Numero di Stanze Disponibili per una Richiesta di Modifica
    @Test
    public void testCheckRoomAvailabilityForModificationRequests() {
        when(roomCategoryService.getAvailableRoomsFromPaidBooking(anyString(), anyString(), anyString())).thenReturn(3);

        ResponseEntity<?> response = roomCategoryController.checkRoomAvailabilityForModificationRequests("Double Room", "2024-02-01", "2024-02-15");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Map.of("available", 3), response.getBody());

        verify(roomCategoryService, times(1)).getAvailableRoomsFromPaidBooking("Double Room", "2024-02-01", "2024-02-15");
    }
}
*/