package in.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.dto.UserRequest;
import in.main.entity.Userdtls;
import in.main.repositoty.UserDtlsRepositoty;
import in.main.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepositoty userDtlsRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public Boolean register(UserRequest userRequest) {
		Userdtls userdtls=UserServiceImpl(userRequest);
		String password=passwordEncoder.encode(userdtls.getPassword());
		userdtls.setPassword(password);
		Userdtls save=userDtlsRepository.save(userdtls);
		if(!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public String login(UserRequest userRequest) {
		Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				userRequest.getUsername(),userRequest.getPassword()));
		if(authenticate.isAuthenticated()) {
		    return jwtService.generatetoken(userRequest.getUsername());
		}
		return null;
	}

	
	
	public Userdtls UserServiceImpl(UserRequest userRequest) {
		Userdtls userdtls=new Userdtls();
		userdtls.setUsername(userRequest.getUsername());
		userdtls.setPassword(userRequest.getPassword());
		return userdtls;
	}
	
	public UserRequest  UserServiceImpl(Userdtls userdtls) {
		UserRequest userRequest=new UserRequest();
		userRequest.setUsername(userdtls.getUsername());
		userRequest.setPassword(userdtls.getPassword());
		return userRequest;
	}

}
