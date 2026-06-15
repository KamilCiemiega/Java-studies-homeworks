package tickethub.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tickethub.cart.Cart;
import tickethub.cart.CartItem;
import tickethub.entity.Event;
import tickethub.service.EventService;
import tickethub.service.ReservationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final EventService eventService;
    private final ReservationService reservationService;

    // POBIERZ KOSZYK Z SESJI
    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    // DODAJ DO KOSZYKA
    @PostMapping("/add/{eventId}")
    public String addToCart(@PathVariable Long eventId, HttpSession session) {

        Event event = eventService.getById(eventId);

        Cart cart = getCart(session);

        cart.addItem(new CartItem(
                event.getId(),
                event.getTitle(),
                1
        ));

        return "redirect:/";
    }

    // PODGLĄD KOSZYKA
    @GetMapping
    public String viewCart(HttpSession session, org.springframework.ui.Model model) {

        Cart cart = getCart(session);

        model.addAttribute("cart", cart);

        return "cart";
    }

    // FINALIZACJA ZAKUPU
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {

        Cart cart = getCart(session);

        for (CartItem item : cart.getItems()) {
            for (int i = 0; i < item.getQuantity(); i++) {
                reservationService.createReservation(
                        item.getEventId(),
                        new tickethub.entity.Reservation()
                );
            }
        }

        cart.clear();

        return "redirect:/";
    }
}