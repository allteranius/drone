package allteranius.demo.drone.services;

import allteranius.demo.drone.repositories.Drone;
import allteranius.demo.drone.repositories.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class ChargeService {

    @Autowired
    DroneRepository droneRepository;

    @Scheduled(cron = "0 1/5 * * * *")
    public void sendDroneToCharge() throws Exception {
        for (Drone drone : droneRepository.findByBatteryStateLessThanEqual(25)) {
            this.chargeDrone(drone);
        }
    }

    @Async
    private void chargeDrone(Drone drone) throws Exception {
        while (drone.getBatteryState() < 100) {
            drone.setBatteryState(drone.getBatteryState() + 5);
            Thread.sleep(10000);
        }
    }
}