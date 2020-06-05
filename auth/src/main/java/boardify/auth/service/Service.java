package boardify.auth.service;

import boardify.auth.dto.AuthenticationResponse;

public interface Service {

    AuthenticationResponse login(String username, String password);
}
