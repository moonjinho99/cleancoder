package com.board.clean.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.board.clean.post.domain.Post;
import com.board.clean.post.domain.Post.PostType;
import com.board.clean.post.dto.PostDataResponseDto;
import com.board.clean.post.dto.PostWriteRequestDto;
import com.board.clean.post.repository.PostRepository;
import com.board.clean.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public ResponseEntity<?> getPostData()
	{
		List<PostDataResponseDto> dtoList = new ArrayList<PostDataResponseDto>();
		
		try {
			List<Post> postList = postRepository.findAll();
			for(Post post : postList)
			{
				PostDataResponseDto dto = PostDataResponseDto.builder()
											.writeUserName(post.getUser().getName())
											.title(post.getTitle())
											.content(post.getContent())
											.code(post.getCode())
											.language(post.getLanguage())
											.postType(post.getPostType())
											.updateAt(post.getUpdatedAt())
											.build();
				
				dtoList.add(dto);
			}
			
			return ResponseEntity.ok(dtoList);
		}catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
		}
	}
	
	public ResponseEntity<?> writePost(PostWriteRequestDto request)
	{
		
		try {			
			Post post = Post.builder()
						.user(userRepository.findById(request.getWriteUserId()).get())
						.title(request.getTitle())
						.content(request.getContent())
						.code(request.getCode())
						.language(request.getLanguage())
						.postType(PostType.valueOf(request.getPostType()))
						.isActive(true)
						.build();
			
			postRepository.save(post);
			
			return ResponseEntity.ok(Map.of("message","SUCCESS"));
			
		}catch (Exception e) {			
			log.error("FAILED : "+e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","FAILED"));
		}
	}
	
}
