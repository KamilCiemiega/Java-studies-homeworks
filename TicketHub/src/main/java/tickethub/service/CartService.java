package tickethub.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tickethub.cart.Cart;
import tickethub.entity.Event;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final String CART_KEY = "CART";

    private final HttpSession session;

    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute(CART_KEY);

        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_KEY, cart);
        }

        return cart;
    }

    public void addToCart(Event event) {
        getCart().add(event);
    }

    public void clear() {
        getCart().clear();
    }
}