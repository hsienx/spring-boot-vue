package com.rg.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rg.Lang.Const;
import com.rg.expection.CaptchaException;
import com.rg.utills.RedisUtill;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {
	
	private final String loginUrl = "/login";
	
	@Autowired
	RedisUtill redisUtil;
	
	@Autowired
	LoginFailureHandler loginFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		log.info("-- CaptchaFilter url : " + url);
		
		if (loginUrl.equals(url) && request.getMethod().equals("POST")) {
			
			
			try {
				validate(request);
			} catch (CaptchaException e) {
				System.out.println(e.getMessage());
				// 交給登入失敗處理器
				loginFailureHandler.onAuthenticationFailure(request, response, e);
			}
		}
		//成功往下走
		filterChain.doFilter(request, response);
	}

	private void validate(HttpServletRequest request) {
		String code = request.getParameter("code");
		String token = request.getParameter("token");
		if (StringUtils.isBlank(code) || StringUtils.isBlank(token)) {
			throw new CaptchaException("驗證碼不為空");
		}
		if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, token))) {
			throw new CaptchaException("驗證碼不正確");
		}
		// 一次性使用
		redisUtil.hdel(Const.CAPTCHA_KEY, token);
	}
}