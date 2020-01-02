package boardify.user.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserServiceException extends RuntimeException {

    private UserExceptionType type;
    private HttpStatus httpStatus;

    public UserServiceException() {
    }

    public UserServiceException(String message, UserExceptionType type, HttpStatus httpStatus) {

        super(message);
        this.type = type;
        this.httpStatus = httpStatus;
    }

    public UserServiceException(String message, Throwable cause, UserExceptionType type, HttpStatus httpStatus) {

        super(message, cause);
        this.type = type;
        this.httpStatus = httpStatus;
    }
}
