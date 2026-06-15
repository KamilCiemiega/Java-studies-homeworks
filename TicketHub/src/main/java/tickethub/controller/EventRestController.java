package tickethub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tickethub.entity.Event;
import tickethub.repository.EventRepository;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventRestController {

    private final EventRepository eventRepository;

    // GET ALL
    @GetMapping
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // CREATE
    @PostMapping
    public Event create(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event updated) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(updated.getTitle());
        event.setDescription(updated.getDescription());
        event.setLocation(updated.getLocation());
        event.setEventDate(updated.getEventDate());
        event.setTicketPrice(updated.getTicketPrice());
        event.setAvailableTickets(updated.getAvailableTickets());

        return eventRepository.save(event);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventRepository.deleteById(id);
    }
}