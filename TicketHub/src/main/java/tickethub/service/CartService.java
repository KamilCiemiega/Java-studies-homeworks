package tickethub.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tickethub.cart.Cart;
import tickethub.cart.CartItem;
import tickethub.entity.Event;

@Service
@RequiredArgsConstructor
public class CartService {

    private final HttpSession session;

    private static final String CART_KEY = "CART";

    public Cart getCart() {
        Cart cart = (Cart) session.getAttribute(CART_KEY);

        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_KEY, cart);
        }

        return cart;
    }

    public void addEvent(Event event) {

        Cart cart = getCart();

        cart.addItem(new CartItem(
                event.getId(),
                event.getTitle(),
                1
        ));
    }

    public void clear() {
        session.removeAttribute(CART_KEY);
    }
}