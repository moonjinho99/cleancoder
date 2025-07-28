package com.board.clean.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.board.clean.post.domain.Post;
import com.board.clean.post.domain.Post.PostType;
import com.board.clean.post.domain.PostComment;
import com.board.clean.post.dto.PostCommentResponseDto;
import com.board.clean.post.dto.PostCommentWriteDto;
import com.board.clean.post.dto.PostDataResponseDto;
import com.board.clean.post.dto.PostWriteRequestDto;
import com.board.clean.post.repository.PostCommentRepository;
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
	private final PostCommentRepository postCommentRepository;
	
	public ResponseEntity<?> getPostData()
	{
		List<PostDataResponseDto> dtoList = new ArrayList<PostDataResponseDto>();		
		
		List<Post> postList = postRepository.findAll();		
		

		for(Post post : postList)
		{
			PostDataResponseDto dto = PostDataResponseDto.builder()
										.writeUserName(post.getUser().getName())
										.postId(post.getId())
										.title(post.getTitle())
										.content(post.getContent())
										.code(post.getCode())
										.language(post.getLanguage())
										.postType(post.getPostType())
										.updateAt(post.getUpdatedAt())
										.commentCnt(postCommentRepository.countComment(post.getId()))
										.build();
			
			dtoList.add(dto);
		}
		
		return ResponseEntity.ok(dtoList);
		
	}
	
	public ResponseEntity<?> writePost(PostWriteRequestDto request)
	{							
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
		
	}
	
	public ResponseEntity<?> detailPost(Long id)
	{
		
		Post post = postRepository.findById(id).get();
		String writeUserName = userRepository.getById(post.getUser().getId()).getName();		
		Long commentCnt = postCommentRepository.countComment(id);
		
		PostDataResponseDto postData = PostDataResponseDto.builder()
										.writeUserName(writeUserName)
										.postId(post.getId())
										.title(post.getTitle())
										.content(post.getContent())
										.code(post.getCode())
										.language(post.getLanguage())
										.postType(post.getPostType())
										.updateAt(post.getUpdatedAt())
										.commentCnt(commentCnt)
										.build();
		
		return ResponseEntity.ok(postData);

	}
	
	public ResponseEntity<?> selectComment(Long id)
	{

		List<PostComment> comments = postCommentRepository.findByPostId(id);
		List<PostCommentResponseDto> commentDtoList = new ArrayList<>();		
		
		for(PostComment comment : comments)
		{
			PostCommentResponseDto commentDto = PostCommentResponseDto.builder()
												.postId(comment.getPost().getId())
												.writeUserId(comment.getUser().getId())
												.writeUserName(comment.getUser().getName())
												.content(comment.getContent())
												.createdAt(comment.getCreatedAt())
												.build();
			commentDtoList.add(commentDto);
		}
		
		return ResponseEntity.ok(commentDtoList);			
		
	}
	
	public ResponseEntity<?> writeComment(PostCommentWriteDto request)
	{
		PostComment comment = PostComment.builder()
							  .user(userRepository.findById(request.getWriteUserId()).get())
							  .post(postRepository.findById(request.getPostId()).get())
							  .content(request.getContent())
							  .build();
		
		postCommentRepository.save(comment);
		
		return ResponseEntity.ok(Map.of("message","SUCCESS"));	
	}
}
