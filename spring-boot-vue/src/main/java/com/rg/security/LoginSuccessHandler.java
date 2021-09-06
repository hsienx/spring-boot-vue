package com.rg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rg.Lang.Result;
import com.rg.utills.JwtUtill;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	JwtUtill jwtUtill;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		
		
		ServletOutputStream outputStream = response.getOutputStream();
		// jwt 放置請求頭
		String jwt = jwtUtill.generateToken(authentication.getName());
		response.setHeader(jwtUtill.getHeader(), jwt);
		
		log.info("-- LoginSuccessHandler header : " + jwtUtill.getHeader() +" : " + jwt  );
		
		Result result = Result.success("");
		// 將result轉json
		outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
	}
}
