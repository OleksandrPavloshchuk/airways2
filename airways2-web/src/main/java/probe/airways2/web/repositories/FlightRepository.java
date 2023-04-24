package probe.airways2.web.repositories;

import org.springframework.data.repository.CrudRepository;
import probe.airways2.domain.Flight;

public interface FlightRepository extends CrudRepository<Flight, String> {

}
