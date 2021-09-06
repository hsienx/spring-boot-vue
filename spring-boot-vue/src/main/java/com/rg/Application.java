package com.rg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.rg.*"})
public class Application extends SpringBootServletInitializer {
	//打包繼承 SpringBootServletInitializer 並複寫以下
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	 /**
     *打包新增此方法
     */
     @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       // 這裡要指向原先用main方法執行的Application啟動類
       return builder.sources(Application .class);
   }

}
