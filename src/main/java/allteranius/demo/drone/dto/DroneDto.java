package allteranius.demo.drone.dto;

import java.util.UUID;

public record DroneDto(
        String name,
        Long id,
        String model,
        int battery,
        UUID orderId
) {
}
