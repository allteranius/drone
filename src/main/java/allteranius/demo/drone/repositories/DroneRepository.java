package allteranius.demo.drone.repositories;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface DroneRepository extends ListCrudRepository<Drone, Long> {
    List<Drone> findByModelCarryingWeightGreaterThanEqualAndBatteryStateGreaterThanAndOrderIdIsNull(float weight, int batteryState);
    List<Drone> findByBatteryStateLessThanEqual(int batteryState);
}
