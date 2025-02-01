/*
package hotel.controllers;

import hotel.models.PaidBooking;
import hotel.models.RequestBooking;
import hotel.services.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    // Attributi
    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // **CASO D'USO N°1 (UC1): EFFETTUA RICHIESTA DI PRENOTAZIONE**

    // Test dell'Endpoint per Creare una Richiesta di Prenotazione
    @Test
    public void testRequestBooking() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        doNothing().when(bookingService).requestBooking(anyString(), anyString(), anyString(), anyString());

        ResponseEntity<?> response = bookingController.requestBooking("Single Room", "2024-01-01", "2024-01-10");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Booking request submitted successfully!", response.getBody());

        verify(bookingService, times(1)).requestBooking(anyString(), anyString(), anyString(), anyString());
    }

    // **CASO D'USO N°2 (UC2): GESTISCI RICHIESTA DI PRENOTAZIONE**

    // Test dell'Endpoint per Visualizzare la Lista delle Richieste di Prenotazione
    @Test
    public void testGetRequestsBooking() {
        List<Map<String, Object>> mockRequests = Collections.singletonList(Map.of("id", 1, "roomType", "Single Room"));
        when(bookingService.getRequestsBooking()).thenReturn(mockRequests);

        ResponseEntity<?> response = bookingController.getRequestsBooking();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockRequests, response.getBody());

        verify(bookingService, times(1)).getRequestsBooking();
    }

    // Test dell'Endpoint per Gestire una Richiesta di Prenotazione
    @Test
    public void testManageRequestBooking() {
        doNothing().when(bookingService).manageRequestBooking(anyString(), anyInt());

        ResponseEntity<?> response = bookingController.manageRequestBooking("Approved", 1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Status updated successfully for booking ID: 1", response.getBody());

        verify(bookingService, times(1)).manageRequestBooking("Approved", 1);
    }

    // **CASO D'USO N°3 (UC3): EFFETTUA PAGAMENTO**

    // Test dell'Endpoint per Visualizzare la Lista delle Richieste di Prenotazione valide per il Pagamento
    @Test
    public void testGetPayments() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        List<RequestBooking> mockPayments = Collections.singletonList(new RequestBooking());
        when(bookingService.getPayments(anyString())).thenReturn(mockPayments);

        ResponseEntity<?> response = bookingController.getPayments();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPayments, response.getBody());

        verify(bookingService, times(1)).getPayments(anyString());
    }

    // Test dell'Endpoint per Effettuare il Pagamento di una Richiesta di Prenotazione
    @Test
    public void testSetPayment() {
        doNothing().when(bookingService).setPayment(anyString(), anyString(), anyString(), anyInt(), anyString(), anyInt());

        ResponseEntity<?> response = bookingController.setPayment("Credit Card", "1234 5678 9012 3456", "12/2025", 123, "John Doe", 1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Payment successfully processed for booking ID: 1", response.getBody());

        verify(bookingService, times(1)).setPayment(anyString(), anyString(), anyString(), anyInt(), anyString(), anyInt());
    }

    // **CASO D'USO N°4 (UC4): VISUALIZZA DETTAGLI DELLA PRENOTAZIONE**

    // Test dell'Endpoint per Visualizzare la Lista delle Prenotazioni Pagate di un Cliente
    @Test
    public void testGetPaidBookingsForCustomer() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        List<PaidBooking> mockPaidBookings = Collections.singletonList(new PaidBooking());
        when(bookingService.getPaidBookingsForCustomer(anyString())).thenReturn(mockPaidBookings);

        ResponseEntity<?> response = bookingController.getPaidBookingsForCustomer();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPaidBookings, response.getBody());

        verify(bookingService, times(1)).getPaidBookingsForCustomer(anyString());
    }

    // **CASO D'USO N°5 (UC5): RICHIEDI MODIFICA DELLA PRENOTAZIONE**

    // Test dell'Endpoint per Richiedere una Modifica di una Prenotazione
    @Test
    public void testRequestModifyBooking() {
        doNothing().when(bookingService).requestModifyBooking(anyInt(), anyString(), anyString());

        ResponseEntity<?> response = bookingController.requestModifyBooking(1, "2024-01-01", "2024-01-10");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Booking modification request submitted successfully!", response.getBody());

        verify(bookingService, times(1)).requestModifyBooking(anyInt(), anyString(), anyString());
    }

    // **CASO D'USO N°6 (UC6): MODIFICA DETTAGLI DELLA PRENOTAZIONE**

    // Test dell'Endpoint per Ottenere la Lista delle Richieste di Modifica
    @Test
    public void testGetModificationRequests() {
        List<Map<String, Object>> mockRequests = Collections.singletonList(Map.of("id", 1, "approved", true));
        when(bookingService.getModificationRequests()).thenReturn(mockRequests);

        ResponseEntity<?> response = bookingController.getModificationRequests();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockRequests, response.getBody());

        verify(bookingService, times(1)).getModificationRequests();
    }

    // Test di Endpoint per Gestire una Richiesta di Modifica di una Prenotazione confermata da un Pagamento Precedente
    @Test
    public void testManageRequestModifyBooking() {
        Map<String, Object> mockRequestBody = Map.of(
            "idBooking", 1,
            "approved", true,
            "originalStartDate", "2024-01-01",
            "originalEndDate", "2024-01-10"
        );

        ResponseEntity<?> response = bookingController.manageRequestModifyBooking(mockRequestBody);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Booking modification reviewed successfully!", response.getBody());
        verify(bookingService, times(1)).manageRequestModifyBooking(1, true, "2024-01-01", "2024-01-10");
    }

    // **CASO D'USO N°7 (UC7): ANNULLA PRENOTAZIONE**

    // Test dell'Endpoint per Annullare una Prenotazione da parte del Cliente
    @Test
    public void testCancelBookingForCustomer() {
        doNothing().when(bookingService).cancelBookingForCustomer(anyInt());

        ResponseEntity<?> response = bookingController.cancelBookingForCustomer(1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Booking successfully cancelled.", response.getBody());

        verify(bookingService, times(1)).cancelBookingForCustomer(1);
    }

    // **CASO D'USO N°8 (UC8): CANCELLA PRENOTAZIONE**

    // Test dell'Endpoint per Visualizzare la Lista delle Prenotazioni Confermate per il Receptionist
    @Test
    public void testGetPaidBookingsForReceptionist() {
        List<PaidBooking> mockPaidBookings = Collections.singletonList(new PaidBooking());
        when(bookingService.getPaidBookingsForReceptionist()).thenReturn(mockPaidBookings);

        ResponseEntity<?> response = bookingController.getPaidBookingsForReceptionist();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPaidBookings, response.getBody());

        verify(bookingService, times(1)).getPaidBookingsForReceptionist();
    }

    // Test dell'Endpoint per Cancellare una Prenotazione da parte del Receptionist
    @Test
    public void testCancelBookingForReceptionist() {
        doNothing().when(bookingService).cancelBookingForReceptionist(anyInt());

        ResponseEntity<?> response = bookingController.cancelBookingForReceptionist(1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Booking and associated payment cancelled successfully!", response.getBody());

        verify(bookingService, times(1)).cancelBookingForReceptionist(1);
    }

    // **CASO D'USO N°9 (UC9): CHECK-IN IN DIGITALE**

    // Test dell'Endpoint per Visualizzare le Prenotazioni Pronte per il Check-In
    @Test
    public void testGetPaidBookingsReadyForCheckIn() {
        List<PaidBooking> mockBookings = Collections.singletonList(new PaidBooking());
        when(bookingService.getPaidBookingsReadyForCheckIn()).thenReturn(mockBookings);

        ResponseEntity<?> response = bookingController.getPaidBookingsReadyForCheckIn();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockBookings, response.getBody());

        verify(bookingService, times(1)).getPaidBookingsReadyForCheckIn();
    }

    // Test dell'Endpoint per Confermare il Check-In
    @Test
    public void testConfirmCheckIn() {
        when(bookingService.confirmCheckIn(anyInt())).thenReturn(true);

        ResponseEntity<?> response = bookingController.confirmCheckIn(1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Check-in confirmed successfully.", response.getBody());

        verify(bookingService, times(1)).confirmCheckIn(1);
    }

    // **CASO D'USO N°10 (UC10): CHECK-OUT IN DIGITALE**

    // Test dell'Endpoint per Visualizzare le Prenotazioni Pronte per il Check-Out
    @Test
    public void testGetPaidBookingsReadyForCheckOut() {
        List<PaidBooking> mockBookings = Collections.singletonList(new PaidBooking());
        when(bookingService.getPaidBookingsReadyForCheckOut()).thenReturn(mockBookings);

        ResponseEntity<?> response = bookingController.getPaidBookingsReadyForCheckOut();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockBookings, response.getBody());

        verify(bookingService, times(1)).getPaidBookingsReadyForCheckOut();
    }

    // Test dell'Endpoint per Confermare il Check-Out
    @Test
    public void testConfirmCheckOut() {
        when(bookingService.confirmCheckOut(anyInt())).thenReturn(true);

        ResponseEntity<?> response = bookingController.confirmCheckOut(1);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Check-out confirmed successfully.", response.getBody());

        verify(bookingService, times(1)).confirmCheckOut(1);
    }
}
*/