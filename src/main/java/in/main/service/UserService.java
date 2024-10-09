package in.main.service;

import in.main.dto.UserRequest;

public interface UserService {

	Boolean register(UserRequest userRequest);
	String login(UserRequest userRequest);
}
