package tickethub.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketAsyncService {

    @Async
    public void generateTicket(Long reservationId) {
        try {
            Thread.sleep(3000);
            log.info("Ticket generated for reservation {}", reservationId);
        } catch (InterruptedException e) {
            log.error("Async ticket generation interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}