package allteranius.demo.drone.repositories;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface DroneRepository extends ListCrudRepository<Drone, Long> {
    List<Drone> findByModelCarryingWeightGreaterThanEqual(float weight);
}
