package com.board.clean.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.board.clean.global.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private static final String[] ALLOWED_BACK_PATH = {
			"/api/auth/**",
			"/login",
			"/signup",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };
	
	private static final String[] ALLOWED_PAGE_PATH = {
			"/login",
			"/signup"           
    };

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
				      
	    http
	    	.cors(Customizer.withDefaults())
	        .csrf(csrf -> csrf.disable())
	        .httpBasic(httpBasic -> httpBasic.disable())
	        .formLogin(form -> form.disable())
	        .authorizeHttpRequests(auth -> auth
	        	.requestMatchers(ALLOWED_PAGE_PATH).permitAll()
	            .requestMatchers(ALLOWED_BACK_PATH).permitAll()
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();		
	}
				
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
