package com.board.clean.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
		log.info("reqeust : "+request.toString());
		
		return postService.writePost(request);
	}
}
