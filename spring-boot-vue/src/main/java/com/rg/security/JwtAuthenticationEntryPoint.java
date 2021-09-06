package com.rg.security;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.rg.Lang.Result;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;


	@Slf4j
	@Component  //異常處理
	public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
		
	 @Override
	 public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	       throws IOException {
		 
	    log.info("JwtAuthenticationEntryPoint : set status " +  HttpServletResponse.SC_UNAUTHORIZED);
	    
	    response.setContentType("application/json;charset=UTF-8");
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  //set status =401
	    ServletOutputStream outputStream = response.getOutputStream();
	    
	    Result result = Result.fail("請先登入!");
	    outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
	    outputStream.flush();
	    outputStream.close();
	 }
	}

