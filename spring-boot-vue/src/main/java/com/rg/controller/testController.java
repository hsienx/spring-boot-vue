package com.rg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rg.Lang.Result;
import com.rg.service.MemberService;

@RestController
public class testController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
//	@GetMapping("/test")
//	@ResponseBody
//	public Result indexPage() {
//		
//		return Result.success( memberService.findlikeMembers(""));
//	}
	
//	@GetMapping("/test/pass")
//	@ResponseBody
//	public Result x() {
//		String pass =bCryptPasswordEncoder.encode("123");
//		boolean matches =bCryptPasswordEncoder.matches("123", pass);
//		System.out.println(" 結果 = " +matches);
//		return new Result().success(pass);
//	}

}
