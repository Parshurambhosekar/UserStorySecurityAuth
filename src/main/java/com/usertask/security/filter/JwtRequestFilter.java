package com.usertask.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.usertask.security.credentialconfig.MyUserDetailsService;
import com.usertask.security.utils.JwtUtils;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private MyUserDetailsService service;
	
	@Autowired
	private JwtUtils jwtUtils;


	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader=request.getHeader("Authorization");
		
		String username=null;
		String jwtToken=null;
		
		if(authorizationHeader!=null  && authorizationHeader.startsWith("Bearer ")) {
			
			jwtToken = authorizationHeader.substring(7);
			
			username = jwtUtils.extractUsername(jwtToken);		
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
				UserDetails userDetails = this.service.loadUserByUsername(username);
				
				if(jwtUtils.validateToken(jwtToken, userDetails)) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
											new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					
					usernamePasswordAuthenticationToken.setDetails(
										new WebAuthenticationDetailsSource().buildDetails(request));
					
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
				}
		}
		
				filterChain.doFilter(request, response);
		
	}

}
