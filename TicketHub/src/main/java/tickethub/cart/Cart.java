package tickethub.cart;

import lombok.Getter;
import tickethub.entity.Event;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {

    private final List<Event> events = new ArrayList<>();

    public void add(Event event) {
        events.add(event);
    }

    public void clear() {
        events.clear();
    }
}