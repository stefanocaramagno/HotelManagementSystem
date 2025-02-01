package hotel.services;

import hotel.models.PaidBooking;
import hotel.models.Payment;
import hotel.models.Customer;
import hotel.models.RequestBooking;
import hotel.models.RoomCategory;
import hotel.repositories.RequestBookingRepository;
import hotel.repositories.PaidBookingRepository;
import hotel.repositories.CustomerRepository;
import hotel.repositories.PaymentRepository;
import hotel.repositories.RoomCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    // Attributi
    @Autowired
    private RequestBookingRepository requestBoookingRepository;

    @Autowired
    private PaidBookingRepository paidBookingRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomCategoryService roomCategoryService;

    // **CASO D'USO N°1 (UC1): EFFETTUA RICHIESTA DI PRENOTAZIONE**

    // Metodo per Aggiungere una Richiesta di Prenotazione di una specifica tipologia di camera in un determinato intervallo di date
    public void requestBooking(String customerEmail, String roomType, String startDate, String endDate) {
        if (roomType == null || roomType.trim().isEmpty() || 
            startDate == null || startDate.trim().isEmpty() || 
            endDate == null || endDate.trim().isEmpty()) {
            throw new RuntimeException("Invalid booking request: missing required fields.");
        }
    
        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null) {
            throw new RuntimeException("Customer not found with email: " + customerEmail);
        }
            
        RoomCategory roomCategory = roomCategoryRepository.findByRoomType(roomType);
        if (roomCategory == null) {
            throw new RuntimeException("Room type not found: " + roomType);
        }
    
        LocalDate startDateParsed = LocalDate.parse(startDate);
        LocalDate endDateParsed = LocalDate.parse(endDate);
        LocalDate today = LocalDate.now();
    
        if (startDateParsed.isBefore(today)) {
            throw new RuntimeException("Start date cannot be earlier than today.");
        }

        if (startDateParsed.isEqual(endDateParsed)) {
            throw new RuntimeException("Start date and end date cannot be the same.");
        }
    
        if (!startDateParsed.isBefore(endDateParsed)) {
            throw new RuntimeException("Start date must be before end date.");
        }
    
        if (!roomCategoryService.isRoomAvailableFromRequestBooking(roomType, startDate, endDate)) {
            throw new RuntimeException("No rooms available for the selected dates.");
        }
    
        RequestBooking requestBooking = new RequestBooking();
        requestBooking.setCustomer(customer);
        requestBooking.setRoomCategory(roomCategory);
        requestBooking.setStartDate(startDateParsed);
        requestBooking.setEndDate(endDateParsed);
    
        long days = ChronoUnit.DAYS.between(startDateParsed, endDateParsed);
        requestBooking.setTotalPrice(roomCategory.getPriceForNight() * days);
        requestBooking.setStatus("Pending Approval");
    
        requestBoookingRepository.save(requestBooking);
    }

    // **CASO D'USO N°2 (UC2): GESTISCI RICHIESTA DI PRENOTAZIONE**

    // Metodo per Visualizzare la Lista delle Richieste di Prenotazione
    public List<Map<String, Object>> getRequestsBooking() {
        return requestBoookingRepository.findPendingRequests().stream()
                .map(request -> {
                    Map<String, Object> requestMap = new HashMap<>();
                    requestMap.put("idBooking", request.getIdBooking());
                    requestMap.put("roomType", request.getRoomCategory().getRoomType());
                    requestMap.put("customerName", request.getCustomer().getFullName());
                    requestMap.put("startDate", request.getStartDate()); 
                    requestMap.put("endDate", request.getEndDate());    
                    requestMap.put("status", request.getStatus());
                    return requestMap;
                })
                .collect(Collectors.toList());
    }

    // Metodo per Gestire una Richiesta di Prenotazione
    public void manageRequestBooking(String status, Integer idBooking) {
        RequestBooking requestBooking = requestBoookingRepository.findById(idBooking)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found for ID: " + idBooking));
        
        requestBooking.setStatus(status);
        requestBoookingRepository.save(requestBooking);
    }

    // **CASO D'USO N°3 (UC3): EFFETTUA PAGAMENTO**

    // Metodo per Visualizzare la Lista delle Richieste di Prenotazione valide per il Pagamento
    public List<RequestBooking> getPayments(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null) {
            throw new RuntimeException("Customer not found with email: " + customerEmail);
        }
        
        return requestBoookingRepository.findApprovedBookings(customer.getIdCustomer());
    }
    
    // Metodo per Effettuare il Pagamento di una Richiesta di Prenotazione
    @Transactional
    public void setPayment(String paymentMethod, String cardNumber, String expiryDate, int cvv, String cardHolder, int idBooking) {
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new RuntimeException("Payment method cannot be null or empty.");
        }

        if (cardNumber == null || !cardNumber.matches("^\\d{4} \\d{4} \\d{4} \\d{4}$")) {
            throw new RuntimeException("Card number must be numeric and follow the format 'XXXX XXXX XXXX XXXX'.");
        }
        
        if (expiryDate == null || !expiryDate.matches("^(0[1-9]|1[0-2])/\\d{4}$")) {
            throw new RuntimeException("Expiry date must be in the format MM/YYYY and a valid date.");
        }

        String[] expiryParts = expiryDate.split("/");
        int month = Integer.parseInt(expiryParts[0]);
        int year = Integer.parseInt(expiryParts[1]);
        LocalDate today = LocalDate.now();
        LocalDate expiry = LocalDate.of(year, month, 1).withDayOfMonth(1);
        if (expiry.isBefore(today.withDayOfMonth(1))) {
            throw new RuntimeException("Expiry date cannot be in the past.");
        }

        if (String.valueOf(cvv).length() != 3 || cvv <= 0) {
            throw new RuntimeException("CVV must be exactly 3 numeric digits.");
        }

        if (cardHolder == null || !cardHolder.matches("^[a-zA-Z ]+$")) {
            throw new RuntimeException("Cardholder name must only contain letters and spaces.");
        }

        RequestBooking requestBooking = requestBoookingRepository.findById(idBooking)
                .orElseThrow(() -> new RuntimeException("Request booking not found"));

        PaidBooking paidBooking = new PaidBooking();
        paidBooking.setRoomCategory(requestBooking.getRoomCategory());
        paidBooking.setCustomer(requestBooking.getCustomer());
        paidBooking.setStartDate(requestBooking.getStartDate());
        paidBooking.setEndDate(requestBooking.getEndDate());
        paidBooking.setStatus("Paid");
        paidBooking.setTotalPrice(requestBooking.getTotalPrice());

        PaidBooking savedPaidBooking = paidBookingRepository.save(paidBooking);

        requestBoookingRepository.delete(requestBooking);

        Payment payment = new Payment();
        payment.setBooking(savedPaidBooking);
        payment.setPaymentMethod(paymentMethod);
        payment.setCardNumber(cardNumber);
        payment.setExpiryDate(expiryDate);
        payment.setCvv(cvv);
        payment.setCardholderName(cardHolder);

        paymentRepository.save(payment);
    }
    
    // **CASO D'USO N°4 (UC4): VISUALIZZA DETTAGLI DELLA PRENOTAZIONE**
    
    // Metodo per Visualizzare la Lista delle Prenotazioni confermate da un Pagamento Precedente
    public List<PaidBooking> getPaidBookingsForCustomer(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null) {
            throw new RuntimeException("Customer not found for email: " + customerEmail);
        }

        return paidBookingRepository.findPaidBookings();
    }

    // **CASO D'USO N°5 (UC5): RICHIEDI MODIFICA DELLA PRENOTAZIONE**

    // Metodo per Richiedere una Modifica di una Prenotazione confermata da un Pagamento Precedente
    @Transactional
    public void requestModifyBooking(int idBooking, String newStartDate, String newEndDate) {

        if (newStartDate == null || newStartDate.trim().isEmpty()) {
            throw new RuntimeException("New start date cannot be null or empty.");
        }
        if (newEndDate == null || newEndDate.trim().isEmpty()) {
            throw new RuntimeException("New end date cannot be null or empty.");
        }

        LocalDate startDate = LocalDate.parse(newStartDate);
        LocalDate endDate = LocalDate.parse(newEndDate);
        LocalDate today = LocalDate.now();
    
        if (startDate.isBefore(today)) {
            throw new RuntimeException("New Start date cannot be earlier than today.");
        }

        if (startDate.isEqual(endDate)) {
            throw new RuntimeException("New Start date and end date cannot be the same.");
        }

        if (!startDate.isBefore(endDate)) {
            throw new RuntimeException("New Start date must be before end date.");
        }

        PaidBooking paidBooking = paidBookingRepository.findById(idBooking)
                .orElseThrow(() -> new RuntimeException("Booking not found."));
                            
        boolean isAvailable = roomCategoryService.isRoomAvailableFromPaidBooking(
            paidBooking.getRoomCategory().getRoomType(),
                newStartDate,
                newEndDate
        );
        
        if (!isAvailable) {
            throw new RuntimeException("No rooms available for the selected dates.");
        }

        paidBooking.setStartDate(startDate);
        paidBooking.setEndDate(endDate);
        paidBooking.setStatus("Pending Modification");
        paidBookingRepository.save(paidBooking);
    }

    // **CASO D'USO N°6 (UC6): MODIFICA DETTAGLI DELLA PRENOTAZIONE**

    // Metodo per Ottenere la Lista delle Richieste di Modifica delle Prenotazioni
    public List<Map<String, Object>> getModificationRequests() {
        List<PaidBooking> pendingRequests = paidBookingRepository.findPaidBookingsInPendingModification();

        return pendingRequests.stream()
            .map(booking -> {
                Map<String, Object> map = new HashMap<>();
                map.put("idBooking", booking.getIdBooking());
                map.put("customerName", booking.getCustomer().getFullName());
                map.put("currentRoomType", booking.getRoomCategory().getRoomType());
                map.put("currentStartDate", booking.getStartDate().toString());
                map.put("currentEndDate", booking.getEndDate().toString());
                map.put("newStartDate", booking.getStartDate().toString());
                map.put("newEndDate", booking.getEndDate().toString()); 
                return map;
            })
            .collect(Collectors.toList());
    }

    // Metodo per Gestire una Richiesta di Modifica di una Prenotazione confermata da un Pagamento Precedente
    @Transactional
    public void manageRequestModifyBooking(int idBooking, boolean approved, String originalStartDate, String originalEndDate) {
        PaidBooking paidBooking = paidBookingRepository.findById(idBooking)
                .orElseThrow(() -> new RuntimeException("Booking not found."));
    
        if (!"Pending Modification".equals(paidBooking.getStatus())) {
            throw new RuntimeException("Booking is not pending modification.");
        }
    
        if (approved) {
            paidBooking.setStatus("Paid");
        } else {
            paidBooking.setStartDate(LocalDate.parse(originalStartDate));
            paidBooking.setEndDate(LocalDate.parse(originalEndDate));
            paidBooking.setStatus("Paid");
        }
    
        paidBookingRepository.save(paidBooking);
    }

    // **CASO D'USO N°7 (UC7): ANNULLA PRENOTAZIONE**

    // Metodo per Annullare una Prenotazione confermata da un Pagamento Precedente
    @Transactional
    public void cancelBookingForCustomer(int idBooking) {
        PaidBooking paidBooking = paidBookingRepository.findById(idBooking)
                .orElseThrow(() -> new RuntimeException("Booking not found."));
    
        paidBooking.setStatus("Cancelled");
    
        paidBookingRepository.save(paidBooking);
    }
    
    // **CASO D'USO N°8 (UC8): CANCELLA PRENOTAZIONE**

    // Metodo per Visualizzare Lista delle Prenotazioni confermate da un Pagamento Precedente   
    public List<PaidBooking> getPaidBookingsForReceptionist() {
        return paidBookingRepository.findPaidBookings();
    }

    // Metodo per Cancellare una Prenotazione confermata da un Pagamento Precedente   
    @Transactional
    public void cancelBookingForReceptionist(int idBooking) {
        PaidBooking paidBooking = paidBookingRepository.findById(idBooking)
            .orElseThrow(() -> new RuntimeException("Booking not found."));

        paymentRepository.deleteByIdBooking(idBooking);

        paidBookingRepository.delete(paidBooking);
    }

    // **CASO D'USO N°9 (UC9): CHECK-IN IN DIGITALE**

    // Metodo per Visualizzare la Lista delle Prenotazioni confermate da un Pagamento Precedente e abilitate al Check-In
    public List<PaidBooking> getPaidBookingsReadyForCheckIn() {
        return paidBookingRepository.findPaidBookingsReadyForCheckIn();
    }

    // Metodo per Effettuare il Check-In di una Prenotazione confermata da un Pagamento Precedente
    public boolean confirmCheckIn(int idBooking) {
        PaidBooking paidBooking = paidBookingRepository.findById(idBooking).orElse(null);
        if (paidBooking != null) {
            paidBooking.setStatus("Check-In Confirmed");
            paidBookingRepository.save(paidBooking);
            return true;
        }
        return false;
    }
     
    // **CASO D'USO N°10 (UC10): CHECK-OUT IN DIGITALE**

    // Metodo per Visualizzare la Lista delle Prenotazioni confermate da un Pagamento Precedente e abilitate al Check-Out
    public List<PaidBooking> getPaidBookingsReadyForCheckOut() {
        return paidBookingRepository.findPaidBookingsReadyForCheckOut();
    }

    // Metodo per Effettuare il Check-Out di una Prenotazione confermata da un Pagamento Precedente
    public boolean confirmCheckOut(int idBooking) {
        PaidBooking paidBooking = paidBookingRepository.findById(idBooking).orElse(null);
        if (paidBooking != null) {
            paidBooking.setStatus("Check-Out Confirmed");
            paidBookingRepository.save(paidBooking);
            return true;
        }
        return false;
    }

}
