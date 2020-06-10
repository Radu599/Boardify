package boardify.auth.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginServiceException extends RuntimeException {

    private LoginExceptionType loginExceptionType;
    private HttpStatus httpStatus;

    public LoginServiceException(String message, LoginExceptionType loginExceptionType, HttpStatus httpStatus) {

        super(message);
        this.loginExceptionType = loginExceptionType;
        this.httpStatus = httpStatus;
    }
}
