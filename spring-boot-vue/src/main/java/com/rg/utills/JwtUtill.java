package com.rg.utills;

import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;


@Data
@Component
@ConfigurationProperties(prefix = "properties.jwt")
public class JwtUtill {
	
	private long expire;	
	private String secret;
	private String header;
	
	
	//創建 jwt
	public String generateToken(String username) {
		
		Date now =new Date();
		Date expirDate =new Date(now.getTime()+1000 * expire);
		
		return Jwts.builder()
		.setHeaderParam("type", "JWT")
		.setSubject(username)
		.setIssuedAt(now)
		.setExpiration(expirDate) // 7天過期
		.signWith(SignatureAlgorithm.HS512, secret) //Hs512編碼
		.compact();	
	}
	
	//解析
	public Claims getClaimsByToken(String jwt) {
		try {

		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(jwt)
				.getBody();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	//jwt是否過期
	public boolean isTokenExpired(Claims clamis) {
		return clamis.getExpiration().before(new Date());
	}


	
}
