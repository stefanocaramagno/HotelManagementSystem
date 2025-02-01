/* 
package hotel.controllers;

import hotel.models.Receptionist;
import hotel.services.ReceptionistService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReceptionistControllerTest {

    // Attributi
    @Mock
    private ReceptionistService receptionistService;

    @InjectMocks
    private ReceptionistController receptionistController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test dell'Endpoint per la Registrazione del Receptionist
    @Test
    public void testRegisterReceptionist() {
        doNothing().when(receptionistService).registerReceptionist(any(Receptionist.class));

        Receptionist receptionist = new Receptionist();
        receptionist.setFullName("Jane Doe");
        receptionist.setEmail("janedoe@example.com");
        receptionist.setPassword("securepassword");

        ResponseEntity<String> response = receptionistController.registerCustomer(receptionist);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Receptionist registered successfully!", response.getBody());

        verify(receptionistService, times(1)).registerReceptionist(any(Receptionist.class));
    }
}
*/