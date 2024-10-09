package in.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.main.dto.UserRequest;
import in.main.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<?> getDetails(HttpServletRequest request){
	//	String id=request.getSession().getId();
		return new ResponseEntity<>("welcome to my you progrgamming world ",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequest userRequest){
		String token =userService.login(userRequest);
		System.out.println(token);
		if(token==null) {
			return new ResponseEntity<>("invalid creadential",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> create(@RequestBody UserRequest userRequest){
		Boolean save=userService.register(userRequest);
		if(!save) {
			return new ResponseEntity<>("invalid creadential",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<>("register successfully",HttpStatus.CREATED);
		}
	}
}
