/* NON FUNZIONA
package hotel.services;

import hotel.models.CleaningStaff;
import hotel.models.Customer;
import hotel.models.OperationsManager;
import hotel.models.Receptionist;
import hotel.repositories.CustomerRepository;
import hotel.repositories.ReceptionistRepository;
import hotel.repositories.OperationsManagerRepository;
import hotel.repositories.CleaningStaffRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    // Attributi
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @Mock
    private OperationsManagerRepository operationsManagerRepository;

    @Mock
    private CleaningStaffRepository cleaningStaffRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test per il Caricamento di un Customer
    @Test
    public void testLoadUserByUsername_Customer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("customer@example.com");
        customer.setPassword("password");
        when(customerRepository.findByEmail("customer@example.com")).thenReturn(customer);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("customer@example.com");

        assertNotNull(userDetails);
        assertEquals("John Doe|customer@example.com", userDetails.getUsername()); 
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER")));

        verify(customerRepository, times(1)).findByEmail("customer@example.com");
    }

    // Test per il Caricamento di un Receptionist
    @Test
    public void testLoadUserByUsername_Receptionist() {
        Receptionist receptionist = new Receptionist();
        receptionist.setFullName("Jane Smith");
        receptionist.setEmail("receptionist@example.com");
        receptionist.setPassword("password");
        when(receptionistRepository.findByEmail("receptionist@example.com")).thenReturn(receptionist);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("receptionist@example.com");

        assertNotNull(userDetails);
        assertEquals("Jane Smith|receptionist@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_RECEPTIONIST")));

        verify(receptionistRepository, times(1)).findByEmail("receptionist@example.com");
    }

    // Test per il Caricamento di un CleaningStaff
    @Test
    public void testLoadUserByUsername_CleaningStaff() {
        CleaningStaff cleaningStaff = new CleaningStaff();
        cleaningStaff.setFullName("Alice Johnson");
        cleaningStaff.setEmail("cleaningstaff@example.com");
        cleaningStaff.setPassword("password");
        when(cleaningStaffRepository.findByEmail("cleaningstaff@example.com")).thenReturn(cleaningStaff);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("cleaningstaff@example.com");

        assertNotNull(userDetails);
        assertEquals("Alice Johnson|cleaningstaff@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CLEANING_STAFF")));

        verify(cleaningStaffRepository, times(1)).findByEmail("cleaningstaff@example.com");
    }

    // Test per il Caricamento di un OperationsManager
    @Test
    public void testLoadUserByUsername_OperationsManager() {
        OperationsManager operationsManager = new OperationsManager();
        operationsManager.setFullName("Bob Brown");
        operationsManager.setEmail("operationsmanager@example.com");
        operationsManager.setPassword("password");
        when(operationsManagerRepository.findByEmail("operationsmanager@example.com")).thenReturn(operationsManager);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("operationsmanager@example.com");

        assertNotNull(userDetails);
        assertEquals("Bob Brown|operationsmanager@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_OPERATIONS_MANAGER")));

        verify(operationsManagerRepository, times(1)).findByEmail("operationsmanager@example.com");
    }

    // Test per Lanciare un'Eccezione quando l'Utente Non viene Trovato
    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(customerRepository.findByEmail("unknown@example.com")).thenReturn(null);
        when(receptionistRepository.findByEmail("unknown@example.com")).thenReturn(null);
        when(cleaningStaffRepository.findByEmail("unknown@example.com")).thenReturn(null);
        when(operationsManagerRepository.findByEmail("unknown@example.com")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown@example.com");
        });

        assertEquals("User with email 'unknown@example.com' not found", exception.getMessage());

        verify(customerRepository, times(1)).findByEmail("unknown@example.com");
        verify(receptionistRepository, times(1)).findByEmail("unknown@example.com");
        verify(cleaningStaffRepository, times(1)).findByEmail("unknown@example.com");
        verify(operationsManagerRepository, times(1)).findByEmail("unknown@example.com");
    }

}
*/
