package tickethub.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    // ===== DODANIE DO KOSZYKA =====
    @PostMapping("/add/{eventId}")
    public String addToCart(@PathVariable Long eventId, HttpSession session) {
        Event event = eventService.getById(eventId);
        Cart cart = getCart(session);

        cart.addItem(new CartItem(
                event.getId(),
                event.getTitle(),
                1
        ));

        return "redirect:/events";
    }

    // ===== PODGLĄD KOSZYKA =====
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = getCart(session);
        model.addAttribute("cart", cart);
        return "cart";
    }

    // ===== CHECKOUT =====
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        Cart cart = getCart(session);

        reservationService.processCheckout(cart);

        session.removeAttribute("cart");
        return "redirect:/?msg=success";
    }
}