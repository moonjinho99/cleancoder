package com.board.clean.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
	
	private static final String[] ALLOWED_PATH = {
			"/api/auth/**",
			"/login",
			"/signup",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/auth/**",
            "/",
            "/post/**"
           
    };
	
	private static final String[] ACCESS_USER_PATH = {
			"/api/post/**"
    };

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
				      
	    http
	    	.cors(Customizer.withDefaults())
	        .csrf(csrf -> csrf.disable())
	        .httpBasic(httpBasic -> httpBasic.disable())
	        .formLogin(form -> form.disable())
	        .authorizeHttpRequests(auth -> auth
	        	.requestMatchers(ACCESS_USER_PATH).hasRole("USER")
	            .requestMatchers(ALLOWED_PATH).permitAll()
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
