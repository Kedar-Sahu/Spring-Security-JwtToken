package in.main.config;

import java.util.Arrays;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.main.entity.Userdtls;

public class MyUserDetails implements UserDetails{

	private Userdtls userDtls;
	
	public MyUserDetails(Userdtls userDtls) {
		super();
		this.userDtls = userDtls;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority("USER");
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		
		return userDtls.getPassword();
	}

	@Override
	public String getUsername() {
		
		return userDtls.getUsername();
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	

}
