package com.board.clean.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.board.clean.auth.dto.JoinRequestDto;
import com.board.clean.auth.dto.JoinResponseDto;
import com.board.clean.auth.dto.LoginRequestDto;
import com.board.clean.auth.dto.LoginResponseDto;
import com.board.clean.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "인증 관련 API")
public class AuthApiController {

	private final AuthService authService;
		
	@Operation(summary = "로그인 API", description = "이메일과 패스워드로 로그인합니다.")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
		log.info("=======================[Login]=======================");
		return authService.login(request);
	}
	
	@Operation(summary = "회원가입 API", description = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<JoinResponseDto> signup(@RequestBody JoinRequestDto request){
		log.info("=======================[Sign Up]=======================");
		authService.signup(request);
		return ResponseEntity.ok(new JoinResponseDto("회원가입 성공"));
	}
	
	@Operation(summary = "토큰 재발급 API", description = "만료된 access token 재발급")
	@PostMapping("/reissue")
	public ResponseEntity<String> reissueAccessToken(@RequestHeader("Authorization") String expiredAccessToken)
	{
		log.info("=======================[토큰 재발급]=======================");
		String token = expiredAccessToken.substring(7);
        String newAccessToken = authService.reissueAccessToken(token);

        return ResponseEntity.ok(newAccessToken);
	}
	
	@Operation(summary = "로그아웃 API", description = "로그아웃")
	@GetMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken)
	{
		log.info("=======================[Logout]=======================");
		String token = accessToken.substring(7);
        
        return authService.logout(token);
	}
}
