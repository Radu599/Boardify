package boardify.auth.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthServiceException extends RuntimeException {

    private AuthExceptionType type;
    private HttpStatus httpStatus;

    public AuthServiceException(String message, AuthExceptionType type, HttpStatus httpStatus) {
        super(message);
        this.type = type;
        this.httpStatus = httpStatus;
    }

    public AuthServiceException(String message, Throwable cause, AuthExceptionType type, HttpStatus httpStatus) {
        super(message, cause);
        this.type = type;
        this.httpStatus = httpStatus;
    }
}
