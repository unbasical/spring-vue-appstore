package base.appstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidLoginCredentialsException extends RuntimeException {
    public InvalidLoginCredentialsException() {
        super("Your login data are wrong, please check username and password");
    }
}
