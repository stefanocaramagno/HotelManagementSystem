// FUNZIONA

package hotel.services;

import hotel.models.CleaningStaff;
import hotel.repositories.CleaningStaffRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CleaningStaffServiceTest {

    // Attributi
    @Mock
    private CleaningStaffRepository cleaningStaffRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CleaningStaffService cleaningStaffService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test del Metodo per la Registrazione del Cleaning Staff
    @Test
    public void testRegisterCleaningStaff() {
        CleaningStaff mockCleaningStaff = new CleaningStaff();
        mockCleaningStaff.setPassword("plainPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(cleaningStaffRepository.save(mockCleaningStaff)).thenReturn(mockCleaningStaff);

        cleaningStaffService.registerCleaningStaff(mockCleaningStaff);

        assertEquals("encodedPassword", mockCleaningStaff.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(cleaningStaffRepository, times(1)).save(mockCleaningStaff);
    }

    // Test del Metodo per Ottenere Tutti i Membri dello Staff
    @Test
    public void testGetAllCleaningStaff() {
        CleaningStaff staff1 = new CleaningStaff();
        staff1.setIdCleaningStaff(1);
        staff1.setFullName("John Doe");

        CleaningStaff staff2 = new CleaningStaff();
        staff2.setIdCleaningStaff(2);
        staff2.setFullName("Jane Doe");

        List<CleaningStaff> mockStaffList = Arrays.asList(staff1, staff2);

        when(cleaningStaffRepository.findAll()).thenReturn(mockStaffList);

        List<CleaningStaff> result = cleaningStaffService.getAllCleaningStaff();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Jane Doe", result.get(1).getFullName());
        verify(cleaningStaffRepository, times(1)).findAll();
    }
}
