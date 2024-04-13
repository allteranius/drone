package allteranius.demo.drone.services;

import allteranius.demo.drone.dto.CreateDroneDto;
import allteranius.demo.drone.dto.CreateModelDto;
import allteranius.demo.drone.dto.DroneDto;
import allteranius.demo.drone.dto.StartOrderDto;
import allteranius.demo.drone.exceptions.DroneIsNotAvailable;
import allteranius.demo.drone.exceptions.DroneModelNotExists;
import allteranius.demo.drone.exceptions.DroneNotFound;
import allteranius.demo.drone.repositories.Drone;
import allteranius.demo.drone.repositories.DroneModel;
import allteranius.demo.drone.repositories.DroneRepository;
import allteranius.demo.drone.repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneService {

    @Autowired
    DroneRepository droneRepository;
    @Autowired
    ModelRepository modelRepository;


    public Long createDrone(CreateDroneDto createDroneDto) {
        var model = modelRepository.findById(createDroneDto.model());
        if (model.isEmpty()) {
            throw new DroneModelNotExists();
        }
        var drone = new Drone();
        drone.setModel(model.get());
        drone.setBatteryState(100);
        drone.setName(createDroneDto.name());
        return droneRepository.save(drone).getDroneId();
    }

    public boolean addDroneModel(CreateModelDto createModelDto) {
        if (modelRepository.existsById(createModelDto.name())) {
            return false;
        }
        var model = new DroneModel();
        model.setModelName(createModelDto.name());
        model.setCarryingWeight(createModelDto.carryingWeight());
        modelRepository.save(model);
        return true;
    }

    public DroneDto getDrone(Long id) {
        Optional<Drone> drone = droneRepository.findById(id);
        if (drone.isEmpty()) {
            throw new DroneNotFound();
        }
        return mapDrone(drone.get());
    }

    public boolean startOrder(StartOrderDto startOrderDto) {
        var drone = droneRepository.findById(startOrderDto.droneId());
        if (drone.isPresent()){
            Drone droneEntity = drone.get();
            if(droneEntity.getOrderId() == null
                    && droneEntity.getBatteryState() > 25
                    && droneEntity.getModel().getCarryingWeight()> startOrderDto.weight()) {
                droneEntity.setOrderId(startOrderDto.orderId());
                droneRepository.save(droneEntity);
                return true;
            } else {
                throw new DroneIsNotAvailable();
            }
        }
        throw new DroneNotFound();
    }

    public List<DroneDto> findSuitedDrone(int weight) {
        return droneRepository
                .findByModelCarryingWeightGreaterThanEqualAndBatteryStateGreaterThanAndOrderIdIsNull(weight, 25)
                .stream()
                .map(this::mapDrone)
                .toList();
    }

    private DroneDto mapDrone(Drone drone) {
        return new DroneDto(drone.getName(),
                drone.getDroneId(),
                drone.getModel().getModelName(),
                drone.getBatteryState(),
                drone.getOrderId());
    }
}
