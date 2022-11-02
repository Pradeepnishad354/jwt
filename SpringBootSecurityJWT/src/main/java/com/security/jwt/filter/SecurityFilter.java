package com.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.jwt.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException
	{
		// read token from authorization headers
		String token = request.getHeader("Authorization");
		
		if(token!=null) {
			
		// do validate 
			String username = jwtUtil.getUserName(token);
			
			// user should not be empty context authentication must be empty
				
			if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null); 
				
			// load user from database 
			{
				UserDetails usr = userDetailsService.loadUserByUsername(username);
				
				//validate token 
				
				boolean isValid= jwtUtil.validateToken(token, usr.getUsername());
				
				if(isValid) {
					
					UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, usr.getPassword()
							,usr.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					
					//final object is store in Spring Context with User Details(usr, password)
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}
			
			
			
			
			
		}
		
		filterChain.doFilter(request, response);
	}

}
