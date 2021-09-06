package com.rg.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.rg.security.CaptchaFilter;
import com.rg.security.JWTAuthenticationFilter;
import com.rg.security.JwtAccessDeniedHandler;
import com.rg.security.JwtAuthenticationEntryPoint;
import com.rg.security.JwtLogoutSuccessHandler;
import com.rg.security.LoginFailureHandler;
import com.rg.security.LoginSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //post請求前通行
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	LoginFailureHandler loginFailureHandler;
	
	@Autowired
	LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	CaptchaFilter captchaFilter;
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	JwtAccessDeniedHandler JwtAccessDeniedHandler;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
	
	@Bean
	JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JWTAuthenticationFilter jwtAuthenticationFilter =new JWTAuthenticationFilter(authenticationManager());
		return jwtAuthenticationFilter;
	};
	
	@Bean  //加密方法
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	private static final String[] URL_WHITE_LIST =
			{
				"/login",
				//"/singup",
				"/logout",
				"/captcha",
				"/favicon.ico",
				"/add"
					
			};
	
	protected void configure(HttpSecurity http) throws Exception {
		//允許跨域預防攻擊關閉
		http.cors().and().csrf().disable()
		//登入配置
		.formLogin()
			.successHandler(loginSuccessHandler) 
			.failureHandler(loginFailureHandler)
		//登出配置
		.and()
			.logout()
			.logoutSuccessHandler(jwtLogoutSuccessHandler)
			
			
		//禁用session
		.and()
		
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		//攔截規則
		.and()
			.authorizeRequests()
			//允許通過路徑
			.antMatchers(URL_WHITE_LIST).permitAll()
			.anyRequest().authenticated()
		//異常處理
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(null)
			
		//自定義過濾器
			.and()
				
				//圖片過濾器在帳號密碼過濾器之前
				.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
				//加入jwt過濾器
				.addFilter(jwtAuthenticationFilter())
			;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
  
}