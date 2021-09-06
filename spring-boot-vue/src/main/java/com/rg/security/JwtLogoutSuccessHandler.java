package com.rg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.rg.Lang.Result;
import com.rg.utills.JwtUtill;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	JwtUtill jwtUtill;


	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	
		
		ServletOutputStream outputStream = response.getOutputStream();
		// jwt 放置請求頭為空
		if(authentication!=null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		response.setHeader(jwtUtill.getHeader(), "");
		
		log.info("-- JwtLogoutSuccessHandler header : ' ' " );
		
		Result result = Result.success("");
		// 將result轉json
		outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
	}
}
