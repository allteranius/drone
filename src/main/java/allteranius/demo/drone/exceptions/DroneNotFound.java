package allteranius.demo.drone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Drone Not Found")

public class DroneNotFound extends RuntimeException {
}
