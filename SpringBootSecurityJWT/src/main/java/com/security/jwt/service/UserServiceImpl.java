package com.security.jwt.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.jwt.model.User;
import com.security.jwt.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// save method

	@Override
	public Integer saveUser(User user) {
		// password encoder

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user).getId();
	}

	// get user by username
	@Override
	public Optional<User> findByUserName(String username) {

		return userRepository.findByUsername(username);
	}

	// this method is return User from Security not model class
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = findByUserName(username);

		
		

		if (optional.isEmpty())
			throw new UsernameNotFoundException("user not found  ");
		
		// read the user from Db
		User user = optional.get();

		return new org.springframework.security.core.userdetails
				.User(username, user.getPassword(),
				user.getRoles().stream()
				
			.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));

	}

}
