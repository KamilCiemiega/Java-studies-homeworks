package tickethub.cart;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem newItem) {

        for (CartItem item : items) {
            if (item.getEventId().equals(newItem.getEventId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }

        items.add(newItem);
    }

    public void clear() {
        items.clear();
    }
}