package tickethub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tickethub.entity.Reservation;
import tickethub.repository.ReservationRepository;
import tickethub.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    // GET ALL
    @GetMapping
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    // CREATE (rezerwacja przez API)
    @PostMapping("/{eventId}")
    public Reservation create(@PathVariable Long eventId,
                              @RequestBody Reservation reservation) {

        return reservationService.createReservation(eventId, reservation);
    }
}