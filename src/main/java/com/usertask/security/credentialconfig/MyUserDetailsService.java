package com.usertask.security.credentialconfig;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usertask.security.constants.AppConstants;
import com.usertask.security.exception.InvalidCredentialException;
import com.usertask.security.props.AppProperties;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private AppProperties appProperties;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if(AppConstants.USERNAME.equals(username)) {
			
			User user=new User(AppConstants.USERNAME,AppConstants.PASSWORD,new ArrayList<>());
			
			return user;
		}
		else {
			
			throw new InvalidCredentialException(appProperties.getMessages().get(AppConstants.MESSAGE1));
		}
		
	}

}
