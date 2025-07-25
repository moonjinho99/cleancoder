package com.board.clean.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCommentResponseDto {
	
	private Long commentId;
	private Long postId;
	private Long writeUserId;
	private String writeUserName;
	private String content;
	private LocalDateTime createdAt;
	
}
