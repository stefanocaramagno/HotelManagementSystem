/* 
package hotel.controllers;

import hotel.models.CleaningStaff;
import hotel.services.CleaningStaffService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CleaningStaffControllerTest {

    // Attributi
    @Mock
    private CleaningStaffService cleaningStaffService;

    @InjectMocks
    private CleaningStaffController cleaningStaffController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test dell'Endpoint per la Registrazione del Cleaning Staff
    @Test
    public void testRegisterCustomer() {
        doNothing().when(cleaningStaffService).registerCleaningStaff(any(CleaningStaff.class));

        CleaningStaff cleaningStaff = new CleaningStaff();
        cleaningStaff.setFullName("John Doe");
        cleaningStaff.setEmail("johndoe@example.com");
        cleaningStaff.setPassword("securepassword");

        ResponseEntity<String> response = cleaningStaffController.registerCustomer(cleaningStaff);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Cleaning Staff registered successfully!", response.getBody());

        verify(cleaningStaffService, times(1)).registerCleaningStaff(any(CleaningStaff.class));
    }
}
*/
