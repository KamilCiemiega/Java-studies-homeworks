package tickethub.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tickethub.entity.Event;
import tickethub.repository.EventRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;

    @Override
    public void run(String... args) {

        if (eventRepository.count() == 0) {

            Event e1 = new Event();
            e1.setTitle("Avengers");
            e1.setDescription("Marvel movie");
            e1.setLocation("Kraków");
            e1.setEventDate(LocalDateTime.now().plusDays(5));
            e1.setTicketPrice(BigDecimal.valueOf(50));
            e1.setAvailableTickets(100);

            Event e2 = new Event();
            e2.setTitle("Metallica Concert");
            e2.setDescription("Rock concert");
            e2.setLocation("Warszawa");
            e2.setEventDate(LocalDateTime.now().plusDays(20));
            e2.setTicketPrice(BigDecimal.valueOf(300));
            e2.setAvailableTickets(200);

            eventRepository.save(e1);
            eventRepository.save(e2);
        }
    }
}