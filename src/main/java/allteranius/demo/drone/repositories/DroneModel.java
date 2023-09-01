package allteranius.demo.drone.repositories;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Models")
public class DroneModel {
    @Id
    String modelName;
    int carryingWeight;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getCarryingWeight() {
        return carryingWeight;
    }

    public void setCarryingWeight(int carryingWeight) {
        this.carryingWeight = carryingWeight;
    }
}
