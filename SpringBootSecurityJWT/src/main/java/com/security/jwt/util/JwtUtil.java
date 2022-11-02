package com.security.jwt.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("$(app.secret)")
	private String secret;
	
	
	// 1.generate token 
	
	public String generateToken(String subject) {
		
		return Jwts.builder()
				.setSubject(subject) // prepare subject
				.setIssuer("jitendra")//provider
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(10)))
				.signWith(SignatureAlgorithm.HS256,secret.getBytes())
				.compact();
	}
	//2.read claims
	
	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJwt(token)
				.getBody();
		
		
	}
	
	//3.Read expiring date of token
	
	public Date getExpDate(String token) {
		
		return getClaims(token).getExpiration();
	}
	
	//4. Read Subject/Username
	
	public String getUserName(String token) {
		
		return getClaims(token).getSubject();
		
		
	}
	
	//5. validate exipire date
	
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
			return 	expDate.before(new Date(System.currentTimeMillis()));
		
	}
	
	//6. validate username in token and database ,Exp Date
	
	public boolean validateToken(String token, String username) {
		     String tokenUserName=getUserName(token);
		     
		     return username.equals(tokenUserName) && !isTokenExp(token);
		
		
	}
	

}
