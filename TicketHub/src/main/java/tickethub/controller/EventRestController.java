package tickethub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tickethub.entity.Event;
import tickethub.service.EventService;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;

    // Spełnia wymaganie pobierania z filtrami: GET /api/events?location=Krakow&maxPrice=100
    @GetMapping
    public List<Event> getAll(@RequestParam(required = false) String location,
                              @RequestParam(required = false) Double maxPrice) {
        if (location != null || maxPrice != null) {
            return eventService.getFilteredEvents(location, maxPrice);
        }
        return eventService.getAll();
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    // Dodatkowy punkt końcowy pokazujący integrację z API zewnętrznym
    @GetMapping("/{id}/price-usd")
    public BigDecimal getPriceInUSD(@PathVariable Long id) {
        return eventService.getTicketPriceInUSD(id);
    }

    @PostMapping
    public Event create(@RequestBody Event event) {
        return eventService.save(event);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event) {
        return eventService.update(id, event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }
}