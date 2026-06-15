package tickethub.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tickethub.repository.EventRepository;
import tickethub.entity.Event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    public BigDecimal getTicketPriceInUSD(Long eventId) {
        Event event = getById(eventId);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json";

            // Pobieramy dane z NBP
            Map response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("rates")) {
                List<Map<String, Object>> rates = (List<Map<String, Object>>) response.get("rates");
                Double usdRate = (Double) rates.get(0).get("mid");

                // Przeliczamy PLN na USD
                return event.getTicketPrice().divide(BigDecimal.valueOf(usdRate), 2, java.math.RoundingMode.HALF_UP);
            }
        } catch (Exception e) {
            // Fallback w razie gdyby API NBP nie działało
            return event.getTicketPrice().divide(BigDecimal.valueOf(4.0), 2, java.math.RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

}
