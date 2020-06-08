package boardify.auth.service;

<<<<<<< HEAD
import boardify.auth.dto.LoginResponse;
<<<<<<< HEAD
import boardify.auth.dto.RegisterResponse;
=======
>>>>>>> develop

public interface Service {

    LoginResponse login(String username, String password);
<<<<<<< HEAD

    RegisterResponse registerUser(String username, String password);
=======
>>>>>>> develop
=======
import boardify.auth.dto.AuthenticationResponse;

public interface Service {

    AuthenticationResponse login(String username, String password);
>>>>>>> develop
}
