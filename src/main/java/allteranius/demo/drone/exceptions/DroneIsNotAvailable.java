package allteranius.demo.drone.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Drone is not suited")
public class DroneIsNotAvailable extends RuntimeException {
}
