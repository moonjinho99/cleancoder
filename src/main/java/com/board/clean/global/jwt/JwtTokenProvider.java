package com.board.clean.global.jwt;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.board.clean.user.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretkey;
	
	private final long accessTokenValidity = 1000L * 60 * 30;
	private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7;
	
	public String generateAccessToken(User user)
	{
		return createToken(user.getId(), user.getRole().name(), accessTokenValidity);
	}
		
	public String generateRefreshToken(User user)
	{
		return createToken(user.getId(), user.getRole().name(),refreshTokenValidity);
	}
	
	private String createToken(Long id, String role, long validity)
	{
		Date now = new Date();
		
		Claims claims = Jwts.claims().setSubject(Long.toString(id));
		claims.put("role","ROLE_"+role);
		
		Date expiry = new Date(now.getTime() + validity);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiry)
				.signWith(SignatureAlgorithm.HS256, secretkey)
				.compact();
	}
	
	public String getId(String token)
	{
		 if (!validateToken(token)) {
		        throw new JwtException("Invalid token");
		    }
		
		return Jwts.parser().setSigningKey(secretkey)
	            .parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getRole(String token) {
		
		 if (!validateToken(token)) {
		        throw new JwtException("Invalid token");
		    }
		 
        return (String) Jwts.parser().setSigningKey(secretkey)
            .parseClaimsJws(token).getBody().get("role");
    }
	
	public boolean validateToken(String token) {
		try{
			Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.error("token valid error : "+e.getMessage());
            return false;
        }
	}
	
	public long getRefreshTokenExpireTime()
	{
		return refreshTokenValidity;
	}
		
}
