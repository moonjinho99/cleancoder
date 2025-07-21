package com.board.clean.auth.service;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.board.clean.auth.dto.JoinRequestDto;
import com.board.clean.auth.dto.LoginRequestDto;
import com.board.clean.auth.dto.LoginResponseDto;
import com.board.clean.global.jwt.JwtTokenProvider;
import com.board.clean.user.domain.User;
import com.board.clean.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public LoginResponseDto login(LoginRequestDto request) {
		
		User user = userRepository.findByEmail(request.getEmail())
		        .orElseThrow(() -> new UsernameNotFoundException("이메일 없음"));

		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
		{
			throw new BadCredentialsException("패스워드 불일치");
		}
		
		String accessToken = jwtTokenProvider.generateAccessToken(user);
		String refreshToken = jwtTokenProvider.generateRefreshToken(user);
				
		return LoginResponseDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.email(user.getEmail())
				.name(user.getName())
				.build();
	}
	
	public void signup(JoinRequestDto request)
	{
		
		if(userRepository.findByEmail(request.getEmail()).isPresent())
		{
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		User user = User.builder()
					.email(request.getEmail())
					.password(encodedPassword)
					.name(request.getName())
					.role(User.Role.USER)
					.build();
		
		userRepository.save(user);
	}
	
}
