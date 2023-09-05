package allteranius.demo.drone.web;

import allteranius.demo.drone.dto.CreateDroneDto;
import allteranius.demo.drone.dto.CreateModelDto;
import allteranius.demo.drone.dto.DroneDto;
import allteranius.demo.drone.dto.StartOrderDto;
import allteranius.demo.drone.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
public class DroneController {

    @Autowired
    DroneService droneService;

    @PostMapping("/drone")
    public Long createDrone(@RequestBody CreateDroneDto createDroneDto){
        return droneService.createDrone(createDroneDto);
    }
    @PutMapping("/drone_model")
    public boolean createDroneModel(@RequestBody CreateModelDto createModelDto){
        return droneService.addDroneModel(createModelDto);
    }

    @GetMapping("/drone/{id}")
    public DroneDto getDrone(@PathVariable("id") Long id){
        return droneService.getDrone(id);
    }

    @GetMapping("/find_drone")
    public List<DroneDto> findDroneForOrder(@RequestParam int weight){
        return droneService.findSuitedDrone(weight);
    }

    @PostMapping("/start_order")
    public boolean startOrder(@RequestBody StartOrderDto startOrderDto){
        return droneService.startOrder(startOrderDto);
    }

}
