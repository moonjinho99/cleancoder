package com.board.clean.global.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.board.clean.user.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretkey;
	
	private final long accessTokenValidity = 1000L * 60 * 30;
	private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7;
	
	public String generateAccessToken(User user)
	{
		return createToken(user.getId(), accessTokenValidity);
	}
	
	public String generateRefreshToken(User user)
	{
		return createToken(user.getId(), refreshTokenValidity);
	}
	
	private String createToken(Long id, long validity)
	{
		Date now = new Date();
		
		return Jwts.builder()
				.setSubject(Long.toString(id))
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+validity))
				.signWith(SignatureAlgorithm.HS256, secretkey)
				.compact();
	}
	
	public String getId(String token)
	{
		return Jwts.parser()
				.setSigningKey(secretkey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try{
			Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	
}
