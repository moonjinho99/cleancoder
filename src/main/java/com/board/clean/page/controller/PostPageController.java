package com.board.clean.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostPageController {

	@GetMapping("/main")
	public String postMain()
	{
		return "/post/postMain";
	}
	
	@GetMapping("/write")
	public String postWrite()
	{
		return "/post/postWrite";
	}
	
	@GetMapping("/detail")
	public String postDetail()
	{				
		return "/post/postDetail";
	}
	
}
