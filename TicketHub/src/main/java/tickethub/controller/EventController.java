package tickethub.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tickethub.cart.Cart;
import tickethub.entity.Event;
import tickethub.entity.Reservation;
import tickethub.service.CartService;
import tickethub.service.EventService;
import tickethub.service.ReservationService;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final ReservationService reservationService;
    private final CartService cartService;

    @GetMapping("/")
    public String list(Model model) {

        var events = eventService.getAll();

        System.out.println("EVENTS = " + events);

        model.addAttribute("events", events);

        return "events";
    }

    @GetMapping("/event/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getById(id));
        return "event-details";
    }

    @PostMapping("/reserve/{id}")
    public String reserve(@PathVariable Long id,
                          @ModelAttribute Reservation reservation,
                          RedirectAttributes redirectAttributes) {

        try {
            reservationService.createReservation(id, reservation);
            redirectAttributes.addFlashAttribute("msg", "Reservation successful!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/";
    }


    @GetMapping("/events/new")
    public String createForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-form";
    }

    @PostMapping("/events")
    public String save(@ModelAttribute Event event) {
        eventService.save(event);
        return "redirect:/";
    }

}