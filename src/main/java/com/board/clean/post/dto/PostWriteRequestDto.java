package com.board.clean.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostWriteRequestDto {

	private Long writeUserId;
	private String title;
	private String content;
	private String code;
	private String language;
	private String postType;
	
}
