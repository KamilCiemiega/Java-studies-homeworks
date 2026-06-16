package tickethub.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tickethub.cart.Cart;
import tickethub.entity.Event;
import tickethub.entity.Reservation;
import tickethub.repository.EventRepository;
import tickethub.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final TicketAsyncService ticketAsyncService;

    @Transactional
    public Reservation createReservation(Long eventId, Reservation reservation) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (event.getAvailableTickets() <= 0) {
            throw new RuntimeException("Sold out");
        }

        event.setAvailableTickets(event.getAvailableTickets() - 1);
        reservation.setEvent(event);

        Event savedEvent = eventRepository.save(event);
        Reservation savedReservation = reservationRepository.save(reservation);
        ticketAsyncService.generateTicket(savedReservation.getId());

        return savedReservation;
    }

    @Transactional
    public void processCheckout(Cart cart) {
        cart.getItems().forEach(item -> {
            for (int i = 0; i < item.getQuantity(); i++) {
                Reservation reservation = new Reservation();
                reservation.setFirstName("Klient");
                reservation.setLastName("Koszykowy");
                reservation.setEmail("klient@ticket.pl");
                reservation.setReservationDate(java.time.LocalDateTime.now());

                this.createReservation(item.getEventId(), reservation);
            }
        });
    }
}