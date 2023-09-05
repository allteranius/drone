package allteranius.demo.drone.dto;

import java.util.UUID;

public record StartOrderDto(
        UUID orderId,
        Integer weight,
        Long droneId
) {
}
