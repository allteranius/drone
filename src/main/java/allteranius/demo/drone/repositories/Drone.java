package allteranius.demo.drone.repositories;


import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "Drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long droneId;

    @ManyToOne()
    DroneModel model;

    int batteryState;

    String name;

    UUID orderId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public int getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(int batteryState) {
        this.batteryState = batteryState;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
