package tickethub.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tickethub.repository.EventRepository;
import tickethub.entity.Event;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getFilteredEvents(String location, Double maxPrice) {

        List<Event> events = eventRepository.findAll();

        if (location != null) {
            events = events.stream()
                    .filter(e -> e.getLocation() != null &&
                            e.getLocation().toLowerCase().contains(location.toLowerCase()))
                    .toList();
        }

        if (maxPrice != null) {
            events = events.stream()
                    .filter(e -> e.getTicketPrice() != null &&
                            e.getTicketPrice().doubleValue() <= maxPrice)
                    .toList();
        }

        return events;
    }

    public Event update(Long id, Event updated) {
        Event event = getById(id);

        event.setTitle(updated.getTitle());
        event.setLocation(updated.getLocation());
        event.setDescription(updated.getDescription());
        event.setTicketPrice(updated.getTicketPrice());
        event.setEventDate(updated.getEventDate());
        event.setAvailableTickets(updated.getAvailableTickets());

        return eventRepository.save(event);
    }

}
