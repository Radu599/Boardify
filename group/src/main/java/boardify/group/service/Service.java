package boardify.group.service;

import boardify.group.dto.UserDto;

import java.util.List;

public interface Service {

    List<UserDto> findGroupForUser(String email);
}
