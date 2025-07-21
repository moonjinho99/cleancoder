package com.board.clean.auth.dto;

import lombok.Getter;
import lombok.Setter;

//Request에서 Setter를 사용한 이유
//외부에서 들어오는 데이터를 받는 용도이기 때문 기본생성자 + setter 사용
@Getter
@Setter
public class LoginRequestDto {
	private String email;
	private String password;
}
