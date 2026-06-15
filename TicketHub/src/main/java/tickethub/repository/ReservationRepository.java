package tickethub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tickethub.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
