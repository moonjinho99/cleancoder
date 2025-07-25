package com.board.clean.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.clean.post.dto.PostCommentWriteDto;
import com.board.clean.post.dto.PostWriteRequestDto;
import com.board.clean.post.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Tag(name = "Post API", description = "게시글 관련 API")
public class PostController {

	private final PostService postService;
		
	//올라온 게시글 데이터
	@Operation(summary = "게시글 조회 API", description = "모든 게시글을 조회합니다.")
	@GetMapping("/data")
	public ResponseEntity<?> getPostdData()
	{
		log.info("=====================[ Get Post Data ]=====================");
		return postService.getPostData();
	}
	
	@Operation(summary = "게시글 등록 API", description = "게시글을 등록합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> writePost(@RequestBody PostWriteRequestDto request)
	{
		log.info("=====================[ Write Post ]=====================");		
		
		return postService.writePost(request);
	}
		
	@Operation(summary = "게시글 상세 조회 API", description = "게시글을 상세 조회 합니다.")
	@GetMapping("/detail")
	public ResponseEntity<?> detailPost(@RequestParam Long id)
	{
		log.info("=====================[ Detail Post ]=====================");
		return postService.detailPost(id);
	}
		
	@Operation(summary = "게시글 댓글 조회 API", description = "게시글의 댓글을 조회합니다.")
	@GetMapping("/selectComment")
	public ResponseEntity<?> selectComment(@RequestParam Long id)
	{
		log.info("=====================[ Select Post Comment ]=====================");
		return postService.selectComment(id);
	}
	
	@Operation(summary = "게시글 댓글 등록 API", description = "게시글의 댓글을 등록합니다.")
	@PostMapping("/writeComment")
	public ResponseEntity<?> writeComment(@RequestBody PostCommentWriteDto request)
	{
		log.info("=====================[ Write Post Comment ]=====================");
		return postService.writeComment(request);
	}
	
}
