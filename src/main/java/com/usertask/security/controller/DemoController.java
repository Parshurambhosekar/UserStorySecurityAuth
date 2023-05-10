package com.usertask.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.usertask.security.binding.AuthenticationRequest;
import com.usertask.security.binding.AutheticationResponse;
import com.usertask.security.constants.AppConstants;
import com.usertask.security.credentialconfig.MyUserDetailsService;
import com.usertask.security.exception.InvalidCredentialException;
import com.usertask.security.props.AppProperties;
import com.usertask.security.utils.JwtUtils;

@RestController
public class DemoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private AppProperties appProperties;
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
					(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		
		catch (BadCredentialsException e) {
			
				throw new InvalidCredentialException(appProperties.getMessages().get(AppConstants.MESSAGE2));
			
		}
		
	     final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	     
	     final String jwtToken = jwtUtils.generateToken(userDetails);
	     
	     return ResponseEntity.ok(new AutheticationResponse(jwtToken));
	     
	}
	
	
	@GetMapping("/task")
	public String getMessage() {
		
		return appProperties.getMessages().get(AppConstants.MESSAGE3);
		
	}
	
	
	
	
	
}
