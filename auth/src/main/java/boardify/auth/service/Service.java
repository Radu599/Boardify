package boardify.auth.service;

import boardify.auth.dto.LoginResponse;

public interface Service {

    LoginResponse login(String username, String password);
}
