package tickethub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tickethub.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
