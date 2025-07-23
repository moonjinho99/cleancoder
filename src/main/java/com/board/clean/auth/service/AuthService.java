package com.board.clean.auth.service;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.board.clean.auth.dto.JoinRequestDto;
import com.board.clean.auth.dto.LoginRequestDto;
import com.board.clean.auth.dto.LoginResponseDto;
import com.board.clean.auth.repository.RefreshTokenRepository;
import com.board.clean.auth.vo.CustomUserDetails;
import com.board.clean.global.jwt.JwtTokenProvider;
import com.board.clean.user.domain.User;
import com.board.clean.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthenticationManager authenticationManager;
	
	
	public ResponseEntity<?> login(LoginRequestDto request) {				
		try {					

			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));						
			CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();		
			User user = userDetails.getUser();			
			
			String accessToken = jwtTokenProvider.generateAccessToken(user);
			String refreshToken = jwtTokenProvider.generateRefreshToken(user);
			
			refreshTokenRepository.save(user.getId(), refreshToken, jwtTokenProvider.getRefreshTokenExpireTime());		
					
			LoginResponseDto dto = LoginResponseDto.builder()
			.accessToken(accessToken)				
			.email(user.getEmail())
			.name(user.getName())
			.build();			
			
			return ResponseEntity.ok(dto);
		}catch(BadCredentialsException e)
		{			
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "이메일 또는 패스워드가 존재하지 않습니다."));
		}		
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
	
	
	@Transactional
	public String reissueAccessToken(String expiredAccessToken) {
		
		long userId = Long.parseLong(jwtTokenProvider.getId(expiredAccessToken));
		String savedRefreshToken = refreshTokenRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Refresh Token not found. 로그인 필요."));
		
		if(!jwtTokenProvider.validateToken(savedRefreshToken)) {
			throw new RuntimeException("Refresh Token expired or invalid. 로그인 필요.");
		}
		
		String newAccessToken = jwtTokenProvider.generateAccessToken(userRepository.findById(userId).get());
		
		return newAccessToken;
	}
}
