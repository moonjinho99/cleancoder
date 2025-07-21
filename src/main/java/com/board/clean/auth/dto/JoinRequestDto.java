package com.board.clean.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
	private String email;
	private String password;
	private String name;
}
