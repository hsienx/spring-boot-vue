package com.rg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.rg.Lang.Result;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	
 @Override
 public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    ServletOutputStream outputStream = response.getOutputStream();
    
    log.info("LoginFailureHandler : " + exception.getMessage());
    
    Result result = Result.fail(
          "Bad credentials".equals(exception.getMessage()) ? "帳號或密碼錯誤" : exception.getMessage()
    );
    //將result轉json
    outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
    outputStream.flush();
    outputStream.close();
 }
}
