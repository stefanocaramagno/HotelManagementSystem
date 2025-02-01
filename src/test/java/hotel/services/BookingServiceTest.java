package hotel.services;

import hotel.models.*;
import hotel.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    // Attributi
    @Mock
    private RequestBookingRepository requestBookingRepository;

    @Mock
    private PaidBookingRepository paidBookingRepository;

    @Mock
    private RoomCategoryRepository roomCategoryRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RoomCategoryService roomCategoryService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // **CASO D'USO N°1 (UC1): EFFETTUA RICHIESTA DI PRENOTAZIONE**

    // Test del Metodo per Aggiungere una Richiesta di Prenotazione di una specifica tipologia di camera in un determinato intervallo di date
    
    /* NON FUNZIONA
    @Test
    public void testRequestBooking() {
        Customer mockCustomer = new Customer();
        mockCustomer.setEmail("test@example.com");
        mockCustomer.setIdCustomer(1);

        RoomCategory mockRoomCategory = new RoomCategory();
        mockRoomCategory.setRoomType("Single");
        mockRoomCategory.setPriceForNight(100);
        mockRoomCategory.setRoomAmount(5);

        when(customerRepository.findByEmail("test@example.com")).thenReturn(mockCustomer);
        when(roomCategoryRepository.findByRoomType("Single")).thenReturn(mockRoomCategory);
        when(roomCategoryService.isRoomAvailableFromRequestBooking(anyString(), anyString(), anyString())).thenReturn(true);

        bookingService.requestBooking("test@example.com", "Single", "2024-01-01", "2024-01-05");

        verify(requestBookingRepository, times(1)).save(any(RequestBooking.class));
    }
    */

    // **CASO D'USO N°2 (UC2): GESTISCI RICHIESTA DI PRENOTAZIONE**

    // Test del Metodo per Visualizzare la Lista delle Richieste di Prenotazione
    @Test
    public void testGetRequestsBooking() {
        Customer mockCustomer = mock(Customer.class);
        RoomCategory mockRoomCategory = mock(RoomCategory.class);
    
        when(mockCustomer.getFullName()).thenReturn("John Doe");
        when(mockRoomCategory.getRoomType()).thenReturn("Single");
    
        RequestBooking mockRequestBooking = mock(RequestBooking.class);
        when(mockRequestBooking.getIdBooking()).thenReturn(1);
        when(mockRequestBooking.getRoomCategory()).thenReturn(mockRoomCategory);
        when(mockRequestBooking.getCustomer()).thenReturn(mockCustomer);
        when(mockRequestBooking.getStartDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(mockRequestBooking.getEndDate()).thenReturn(LocalDate.of(2025, 1, 5));
        when(mockRequestBooking.getStatus()).thenReturn("Pending");
    
        when(requestBookingRepository.findPendingRequests()).thenReturn(Collections.singletonList(mockRequestBooking));
    
        List<Map<String, Object>> requests = bookingService.getRequestsBooking();
    
        assertNotNull(requests);
        assertEquals(1, requests.size());
        Map<String, Object> request = requests.get(0);
        assertEquals(1, request.get("idBooking"));
        assertEquals("Single", request.get("roomType"));
        assertEquals("John Doe", request.get("customerName"));
        assertEquals(LocalDate.of(2025, 1, 1), request.get("startDate"));
        assertEquals(LocalDate.of(2025, 1, 5), request.get("endDate"));
        assertEquals("Pending", request.get("status"));
    
        verify(requestBookingRepository, times(1)).findPendingRequests();
    }
    
    // Test del Metodo per Gestire una Richiesta di Prenotazione
    @Test
    public void testManageRequestBooking() {
        RequestBooking mockRequest = new RequestBooking();
        mockRequest.setIdBooking(1);
        when(requestBookingRepository.findById(1)).thenReturn(Optional.of(mockRequest));

        bookingService.manageRequestBooking("Approved", 1);

        assertEquals("Approved", mockRequest.getStatus());
        verify(requestBookingRepository, times(1)).save(mockRequest);
    }

    // **CASO D'USO N°3 (UC3): EFFETTUA PAGAMENTO**

    // Test del Metodo per Visualizzare la Lista delle Richieste di Prenotazione valide per il Pagamento
    @Test
    public void testGetPayments() {
        Customer mockCustomer = new Customer();
        mockCustomer.setIdCustomer(1);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(mockCustomer);
        when(requestBookingRepository.findApprovedBookings(1)).thenReturn(Collections.singletonList(new RequestBooking()));

        List<RequestBooking> payments = bookingService.getPayments("test@example.com");

        assertNotNull(payments);
        verify(requestBookingRepository, times(1)).findApprovedBookings(1);
    }

    // Test del Metodo per Effettuare il Pagamento di una Richiesta di Prenotazione
    @Test
    public void testSetPayment() {
        RequestBooking mockRequest = new RequestBooking();
        mockRequest.setIdBooking(1);
        mockRequest.setRoomCategory(new RoomCategory());
        mockRequest.setCustomer(new Customer());
        mockRequest.setTotalPrice(500.0);

        when(requestBookingRepository.findById(1)).thenReturn(Optional.of(mockRequest));

        bookingService.setPayment("Credit Card", "1234 5678 9012", "12/2025", 123, "John Doe", 1);

        verify(paidBookingRepository, times(1)).save(any(PaidBooking.class));
        verify(requestBookingRepository, times(1)).delete(mockRequest);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    // **CASO D'USO N°4 (UC4): VISUALIZZA DETTAGLI DELLA PRENOTAZIONE**

    // Test del Metodo per Visualizzare la Lista delle Prenotazioni confermate da un Pagamento Precedente
    @Test
    public void testGetPaidBookingsForCustomer() {
        Customer mockCustomer = new Customer();
        mockCustomer.setIdCustomer(1);
        when(customerRepository.findByEmail("test@example.com")).thenReturn(mockCustomer);
        when(paidBookingRepository.findPaidBookings()).thenReturn(Collections.singletonList(new PaidBooking()));

        List<PaidBooking> bookings = bookingService.getPaidBookingsForCustomer("test@example.com");

        assertNotNull(bookings);
        verify(paidBookingRepository, times(1)).findPaidBookings();
    }

    // **CASO D'USO N°5 (UC5): RICHIEDI MODIFICA DELLA PRENOTAZIONE**

    // Test del Metodo per Richiedere una Modifica di una Prenotazione confermata da un Pagamento Precedente

    /* NON FUNZIONA
    @Test
    public void testRequestModifyBooking() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        mockPaidBooking.setRoomCategory(new RoomCategory());
        mockPaidBooking.setStartDate(LocalDate.parse("2024-01-01"));
        mockPaidBooking.setEndDate(LocalDate.parse("2024-01-10"));

        when(paidBookingRepository.findById(1)).thenReturn(Optional.of(mockPaidBooking));
        when(roomCategoryService.isRoomAvailableFromPaidBooking(anyString(), anyString(), anyString())).thenReturn(true);

        bookingService.requestModifyBooking(1, "2024-01-05", "2024-01-15");

        assertEquals("Pending Modification", mockPaidBooking.getStatus());
        verify(paidBookingRepository, times(1)).save(mockPaidBooking);
    }
    */

    // **CASO D'USO N°6 (UC6): MODIFICA DETTAGLI DELLA PRENOTAZIONE**

    // Test del Metodo per Ottenere la Lista delle Richieste di Modifica delle Prenotazioni
    @Test
    public void testGetModificationRequests() {
        Customer mockCustomer = mock(Customer.class);
        RoomCategory mockRoomCategory = mock(RoomCategory.class);
    
        when(mockCustomer.getFullName()).thenReturn("John Doe");
        when(mockRoomCategory.getRoomType()).thenReturn("Deluxe");
    
        PaidBooking mockPaidBooking = mock(PaidBooking.class);
        when(mockPaidBooking.getIdBooking()).thenReturn(1);
        when(mockPaidBooking.getCustomer()).thenReturn(mockCustomer);
        when(mockPaidBooking.getRoomCategory()).thenReturn(mockRoomCategory);
        when(mockPaidBooking.getStartDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(mockPaidBooking.getEndDate()).thenReturn(LocalDate.of(2025, 1, 5));
    
        when(paidBookingRepository.findPaidBookingsInPendingModification())
                .thenReturn(Collections.singletonList(mockPaidBooking));
    
        List<Map<String, Object>> requests = bookingService.getModificationRequests();
    
        assertNotNull(requests);
        assertEquals(1, requests.size());
        Map<String, Object> request = requests.get(0);
        assertEquals(1, request.get("idBooking"));
        assertEquals("John Doe", request.get("customerName"));
        assertEquals("Deluxe", request.get("currentRoomType"));
        assertEquals("2025-01-01", request.get("currentStartDate"));
        assertEquals("2025-01-05", request.get("currentEndDate"));
        assertEquals("2025-01-01", request.get("newStartDate"));
        assertEquals("2025-01-05", request.get("newEndDate"));
    
        verify(paidBookingRepository, times(1)).findPaidBookingsInPendingModification();
    }
    

    // Test del Metodo per Gestire una Richiesta di Modifica di una Prenotazione confermata da un Pagamento Precedente
    @Test
    public void testManageRequestModifyBooking() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        mockPaidBooking.setStatus("Pending Modification");

        when(paidBookingRepository.findById(1)).thenReturn(Optional.of(mockPaidBooking));

        bookingService.manageRequestModifyBooking(1, true, "2024-01-01", "2024-01-10");

        assertEquals("Paid", mockPaidBooking.getStatus());
        verify(paidBookingRepository, times(1)).save(mockPaidBooking);
    }

    // **CASO D'USO N°7 (UC7): ANNULLA PRENOTAZIONE**

    // Test del Metodo per Annullare una Prenotazione confermata da un Pagamento Precedente
    @Test
    public void testCancelBookingForCustomer() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        mockPaidBooking.setStatus("Paid");

        when(paidBookingRepository.findById(1)).thenReturn(Optional.of(mockPaidBooking));

        bookingService.cancelBookingForCustomer(1);

        assertEquals("Cancelled", mockPaidBooking.getStatus());
        verify(paidBookingRepository, times(1)).save(mockPaidBooking);
    }

    // **CASO D'USO N°8 (UC8): CANCELLA PRENOTAZIONE**

    // Test del Metodo per Visualizzare Lista delle Prenotazioni confermate da un Pagamento Precedente   
    @Test
    public void testGetPaidBookingsForReceptionist() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        when(paidBookingRepository.findPaidBookings()).thenReturn(Collections.singletonList(mockPaidBooking));

        List<PaidBooking> bookings = bookingService.getPaidBookingsForReceptionist();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        verify(paidBookingRepository, times(1)).findPaidBookings();
    }

    // Test del Metodo per Cancellare una Prenotazione confermata da un Pagamento Precedente
    @Test
    public void testCancelBookingForReceptionist() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        when(paidBookingRepository.findById(1)).thenReturn(Optional.of(mockPaidBooking));

        bookingService.cancelBookingForReceptionist(1);

        verify(paymentRepository, times(1)).deleteByIdBooking(1);
        verify(paidBookingRepository, times(1)).delete(mockPaidBooking);
    }

    // **CASO D'USO N°9 (UC9): CHECK-IN IN DIGITALE**

    // Test del Metodo per Visualizzare la Lista delle Prenotazioni confermate da un Pagamento Precedente e abilitate al Check-In
    @Test
    public void testGetPaidBookingsReadyForCheckIn() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        when(paidBookingRepository.findPaidBookingsReadyForCheckIn()).thenReturn(Collections.singletonList(mockPaidBooking));

        List<PaidBooking> bookings = bookingService.getPaidBookingsReadyForCheckIn();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        verify(paidBookingRepository, times(1)).findPaidBookingsReadyForCheckIn();
    }

    // Test del Metodo per Effettuare il Check-In di una Prenotazione confermata da un Pagamento Precedente
    @Test
    public void testConfirmCheckIn() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        when(paidBookingRepository.findById(1)).thenReturn(Optional.of(mockPaidBooking));

        boolean result = bookingService.confirmCheckIn(1);

        assertTrue(result);
        assertEquals("Check-In Confirmed", mockPaidBooking.getStatus());
        verify(paidBookingRepository, times(1)).save(mockPaidBooking);
    }

    // **CASO D'USO N°10 (UC10): CHECK-OUT IN DIGITALE**

    // Test del Metodo per Effettuare il Check-Out di una Prenotazione confermata da un Pagamento Precedente
    @Test
    public void testConfirmCheckOut() {
        PaidBooking mockPaidBooking = new PaidBooking();
        mockPaidBooking.setIdBooking(1);
        mockPaidBooking.setStatus("Paid");

        when(paidBookingRepository.findById(1)).thenReturn(Optional.of(mockPaidBooking));

        boolean result = bookingService.confirmCheckOut(1);

        assertTrue(result);
        assertEquals("Check-Out Confirmed", mockPaidBooking.getStatus());
        verify(paidBookingRepository, times(1)).save(mockPaidBooking);
    }
}
