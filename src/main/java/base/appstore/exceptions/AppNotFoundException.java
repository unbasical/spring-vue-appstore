package base.appstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AppNotFoundException extends RuntimeException {
    public AppNotFoundException(Long id) {
        super("Could not find app " + id);
    }
}
