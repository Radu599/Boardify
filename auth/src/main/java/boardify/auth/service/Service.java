package boardify.auth.service;

import boardify.auth.dto.LoginResponse;
import boardify.auth.dto.RegisterResponse;

public interface Service {

    LoginResponse login(String username, String password);

    RegisterResponse registerUser(String username, String password);
}
