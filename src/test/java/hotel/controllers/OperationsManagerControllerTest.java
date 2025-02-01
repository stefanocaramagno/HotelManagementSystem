/* 
package hotel.controllers;

import hotel.models.OperationsManager;
import hotel.services.OperationsManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OperationsManagerControllerTest {

    // Attributi
    @Mock
    private OperationsManagerService operationsManagerService;

    @InjectMocks
    private OperationsManagerController operationsManagerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test dell'Endpoint per la Registrazione dell'Operations Manager
    @Test
    public void testRegisterOperationsManager() {
        doNothing().when(operationsManagerService).registerOperationsManager(any(OperationsManager.class));

        OperationsManager operationsManager = new OperationsManager();
        operationsManager.setFullName("John Smith");
        operationsManager.setEmail("johnsmith@example.com");
        operationsManager.setPassword("securepassword");

        ResponseEntity<String> response = operationsManagerController.registerCustomer(operationsManager);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Operations Manager registered successfully!", response.getBody());

        verify(operationsManagerService, times(1)).registerOperationsManager(any(OperationsManager.class));
    }
}
*/