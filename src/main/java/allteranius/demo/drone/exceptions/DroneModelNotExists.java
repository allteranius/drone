package allteranius.demo.drone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Model Not Found")
public class DroneModelNotExists extends RuntimeException{
}
