package com.board.clean.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.clean.auth.dto.JoinRequestDto;
import com.board.clean.auth.dto.JoinResponseDto;
import com.board.clean.auth.dto.LoginRequestDto;
import com.board.clean.auth.dto.LoginResponseDto;
import com.board.clean.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

	private final AuthService authService;
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request){
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<JoinResponseDto> signup(@RequestBody JoinRequestDto request){
		authService.signup(request);
		return ResponseEntity.ok(new JoinResponseDto("회원가입 성공"));
	}
}
