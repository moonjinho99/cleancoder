package com.board.clean.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//직접 만든 데이터를 클라이언트에게 반환하는 용도
//응답 정보는 고정해서 보내기 때문에 불변구조가 바람직하다.
//Builder 패턴을 적용하여 가독성 증가, 필드 순서 무관하게 초기화 가능
@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {	
	private String accessToken;
	private String refreshToken;
	private String email;
	private String name;
	
}