package com.board.clean.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCommentWriteDto {
	
	private Long postId;
	private Long writeUserId;
	private String content;
	
}
