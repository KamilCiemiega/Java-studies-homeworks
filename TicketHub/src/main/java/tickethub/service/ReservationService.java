package tickethub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tickethub.entity.Event;
import tickethub.entity.Reservation;
import tickethub.repository.EventRepository;
import tickethub.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;

    public synchronized void createReservation(Long eventId, Reservation reservation) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableTickets() <= 0) {
            throw new RuntimeException("Sold out");
        }

        event.setAvailableTickets(event.getAvailableTickets() - 1);

        reservation.setEvent(event);

        eventRepository.save(event);
        reservationRepository.save(reservation);
    }
}