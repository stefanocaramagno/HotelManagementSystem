/* 
package hotel.controllers;

import hotel.models.Customer;
import hotel.services.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    // Attributi
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test dell'Endpoint per la Registrazione del Customer
    @Test
    public void testRegisterCustomer() {
        doNothing().when(customerService).registerCustomer(any(Customer.class));

        Customer customer = new Customer();
        customer.setFullName("Jane Doe");
        customer.setEmail("janedoe@example.com");
        customer.setPassword("securepassword");

        ResponseEntity<String> response = customerController.registerCustomer(customer);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Customer registered successfully!", response.getBody());

        verify(customerService, times(1)).registerCustomer(any(Customer.class));
    }
}
*/