package com.board.clean.global.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.board.clean.user.domain.User;
import com.board.clean.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = resolveToken(request);
		
		if(token != null && jwtTokenProvider.validateToken(token)) {
			Long id = Long.parseLong(jwtTokenProvider.getId(token));
			
			User foundUser = userRepository.findById(id)
				    .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(foundUser, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_"+foundUser.getRole())));
	
			SecurityContextHolder.getContext().setAuthentication(auth);
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
