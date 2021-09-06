package com.rg.security;



import java.io.IOException;
import java.util.TreeSet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.rg.utills.JwtUtill;
import com.rg.utills.RedisUtill;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
	
	@Autowired
	JwtUtill jwtUtil;
	
	@Autowired
	RedisUtill redisUtil;
	
	//重寫AuthenticationFilter 需注入securityconfig
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		String jwt = request.getHeader(jwtUtil.getHeader());
		log.info("JWTAuthenticationFilter header jwt : "+ jwt);
		//白名單跳過
		if (StrUtil.isBlankOrUndefined(jwt)) {
			chain.doFilter(request, response);
			return;
		}
		
		Claims claim = jwtUtil.getClaimsByToken(jwt);
		if (claim == null) {
			throw new JwtException("token異常！");
		}
		
		if (jwtUtil.isTokenExpired(claim)){
			throw new JwtException("token已過期");
		}
		
		//使用者訊息
		String username = claim.getSubject();
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(
				username, null, new TreeSet<>());
		
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		log.info(" 成功登入", username);
		chain.doFilter(request, response);
	}
}