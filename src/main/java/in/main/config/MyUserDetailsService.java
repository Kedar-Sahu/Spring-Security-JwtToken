package in.main.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import in.main.entity.Userdtls;
import in.main.repositoty.UserDtlsRepositoty;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDtlsRepositoty userDtlsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Userdtls userDtls=userDtlsRepository.findByUsername(username);
		
		if(userDtls==null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new MyUserDetails(userDtls);
	}
	
	

}
