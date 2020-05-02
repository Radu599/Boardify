package boardify.auth.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RegisterServiceException extends RuntimeException {

    private RegisterExceptionType type;
    private HttpStatus httpStatus;

    public RegisterServiceException(String message, RegisterExceptionType type, HttpStatus httpStatus) {
        super(message);
        this.type = type;
        this.httpStatus = httpStatus;
    }

    public RegisterServiceException(String message, Throwable cause, RegisterExceptionType type, HttpStatus httpStatus) {
        super(message, cause);
        this.type = type;
        this.httpStatus = httpStatus;
    }
}
