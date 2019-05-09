package base.appstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ImageNotStorableException extends RuntimeException {
    public ImageNotStorableException() {
        super("The image you tried to upload couldn't be processed!");
    }
}
