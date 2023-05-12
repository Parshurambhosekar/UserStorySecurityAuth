package com.usertask.security.credentialconfig;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usertask.security.binding.UserDto;
import com.usertask.security.constants.AppConstants;
import com.usertask.security.entity.UserEntity;
import com.usertask.security.exception.InvalidCredentialException;
import com.usertask.security.props.AppProperties;
import com.usertask.security.repository.UserEntityRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private UserEntityRepository entityRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

 		 UserEntity user= entityRepository.findByUsername(username);
 		
 		 if(user==null) {
 			 
			throw new InvalidCredentialException(appProperties.getMessages().get(AppConstants.MESSAGE1));
		}
 		 
 		 return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		
		
		
		/*if (AppConstants.USERNAME.equals(username)) {

			User user = new User(AppConstants.USERNAME, AppConstants.PASSWORD, new ArrayList<>());

			return user;
		} else {

			throw new InvalidCredentialException(appProperties.getMessages().get(AppConstants.MESSAGE1));
		}*/

	}

	public UserEntity saveUser(UserDto dto) {

		UserEntity entity = new UserEntity();
		entity.setUsername(dto.getUsername());
		entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

		return entityRepository.save(entity);
	}
	
	
	

}
