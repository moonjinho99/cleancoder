package com.board.clean.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.board.clean.auth.vo.CustomUserDetails;
import com.board.clean.user.domain.User;
import com.board.clean.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsSerivce implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
		return new CustomUserDetails(user);
	}

}
