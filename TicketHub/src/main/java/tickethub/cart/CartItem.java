package tickethub.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {

    private Long eventId;
    private String eventTitle;
    private int quantity;
}