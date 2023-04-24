package probe.airways2.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import probe.airways2.domain.Airport;

@Repository
public interface AirportRepository extends CrudRepository<Airport, String> {
}
