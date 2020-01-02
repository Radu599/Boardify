package boardify.user.service;

import boardify.user.model.User;

public interface Service {

    String findLocationByEmail(String email);

    void updateLocation(String email, String location);
}
