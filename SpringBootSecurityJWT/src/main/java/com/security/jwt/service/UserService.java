package com.security.jwt.service;

import java.util.Optional;

import com.security.jwt.model.User;

public interface UserService  {
	
Integer	saveUser(User user);

Optional<User> findByUserName(String username);

}
