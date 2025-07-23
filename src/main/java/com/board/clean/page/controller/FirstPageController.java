package com.board.clean.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.board.clean.global.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FirstPageController {
	
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/")
    public String redirectBasedOnAuth(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                return "redirect:/post/main"; 
            }
        }

        return "redirect:/auth/login"; 
    }
}
