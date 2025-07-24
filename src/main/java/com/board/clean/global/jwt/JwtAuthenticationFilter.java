package com.board.clean.global.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.board.clean.auth.service.CustomUserDetailsSerivce;
import com.board.clean.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsSerivce userDetailsService;	
	private final UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = resolveToken(request);
				
		if(token == null)
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		//User의 Pk를 클레임에서 추출 후 인증을 위한 email 추출
		String id = jwtTokenProvider.getId(token);		
		String email = userRepository.findById(Long.parseLong(id)).get().getEmail();
		
		if (id != null && token != null && jwtTokenProvider.validateToken(token)) {
		    Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

		    if (currentAuth == null || !currentAuth.isAuthenticated()) {
		        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		        UsernamePasswordAuthenticationToken authentication =
		            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(authentication);
		    }
		}
							
		filterChain.doFilter(request, response);
	}
	
	
	private String resolveToken(HttpServletRequest request)
	{
		String bearer = request.getHeader("Authorization");
		if(bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		
		return null;
	}
	
}
