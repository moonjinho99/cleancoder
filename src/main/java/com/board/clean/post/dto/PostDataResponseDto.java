package com.board.clean.post.dto;

import java.time.LocalDateTime;
import com.board.clean.post.domain.Post.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class PostDataResponseDto {

	private String writeUserName;
	private Long postId;
	private String title;
	private String content;
	private String code;
	private String language;
	private PostType postType;
	private LocalDateTime updateAt;
	
}
