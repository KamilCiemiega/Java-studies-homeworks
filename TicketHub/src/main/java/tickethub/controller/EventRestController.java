package tickethub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tickethub.entity.Event;
import tickethub.entity.Reservation;
import tickethub.service.EventService;
import tickethub.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;
    private final ReservationService reservationService;

    // GET ALL EVENTS
    @GetMapping
    public List<Event> getAll() {
        return eventService.getAll();
    }

    // GET ONE EVENT
    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    // CREATE EVENT (API)
    @PostMapping
    public Event create(@RequestBody Event event) {
        return eventService.save(event);
    }

    // DELETE EVENT
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }
}