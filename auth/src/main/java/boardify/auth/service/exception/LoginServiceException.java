package boardify.auth.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginServiceException extends RuntimeException {

    private LoginExceptionType type;
    private HttpStatus httpStatus;

    public LoginServiceException(String message, LoginExceptionType type, HttpStatus httpStatus) {
        super(message);
        this.type = type;
        this.httpStatus = httpStatus;
    }

    public LoginServiceException(String message, Throwable cause, LoginExceptionType type, HttpStatus httpStatus) {
        super(message, cause);
        this.type = type;
        this.httpStatus = httpStatus;
    }
}
