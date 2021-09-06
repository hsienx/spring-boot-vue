package com.rg.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.rg.Lang.Const;
import com.rg.Lang.Result;
import com.rg.entity.Member;
import com.rg.entity.UserTable;
import com.rg.service.MemberService;
import com.rg.utills.RedisUtill;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

@Slf4j
@RestController
public class MemberController {
	
	@Autowired
	RedisUtill redisUtil;

	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	private Producer producer;
	

	/**
	 * 圖片驗證碼
	 */
	@GetMapping("/captcha")
	public Result captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String code = producer.createText();
		String key = UUID.randomUUID().toString();
		
		BufferedImage image = producer.createImage(code);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		//轉base64
		BASE64Encoder encoder = new BASE64Encoder();
		String str = "data:image/jpeg;base64,";
		String base64Img = str + encoder.encode(outputStream.toByteArray());
		
		// 存到redis中
		redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
		log.info(" -- captcha : " + key + " code :" +code);
		
		return Result.success(MapUtil.builder()
				.put("token", key)
				.put("captchImg", base64Img)
				.build());
	}


	@PostMapping("/add") 
	public Result addMember(@RequestBody Member member) {
		if(memberService.findByAccount(member.getUsername())==null) {
			log.info("此帳號使用中");
			return Result.fail("此帳號使用中");
		}
		member.setPassword(bcryptEncoder.encode(member.getPassword()));
		member.setUpdatetime(LocalDateTime.now());
		member.setStatus("0");
		memberService.addMember(member);
		return Result.success("");

	}
	
	
	@GetMapping("/getall")
	public Result getAll(String account) throws ServletRequestBindingException {
		UserTable ut=new UserTable();
		log.info("getall or  get account =" + account);
		int size=ServletRequestUtils.getIntParameter(req, "size",9); 
		int current=ServletRequestUtils.getIntParameter(req, "current",1);

		
		Pageable pageable = PageRequest.of(current-1, size,Sort.by("MEMBER_ID").descending());
		Page<Member> result= memberService.findlikeMembers(account,pageable);
		
		List<Member> list=result.getContent();
		
		ut.setTotal(result.getTotalPages()); //總頁數
		ut.setSize(size);					//筆數
		ut.setCurrent(current);				//當前頁
		ut.setMembers(list);
		log.info("total : " + result.getTotalPages() ); 
		return Result.success(ut);
		
	}
	
	@GetMapping("/getbyid/{id}")
	public Result getById(@PathVariable(name ="id") Long id) {
		log.info("controller getbyid : " + id);
		
		return  Result.success(memberService.finMemberbyid(id));
		
	}

	@PutMapping("/update")
	public Result update(@RequestBody Member member) {
		log.info("controller update");
		member.setUpdatetime(LocalDateTime.now());
		memberService.update(member);
		return Result.success("");

	}
	
	@PutMapping("/updatepwd")
	public Result updatePwd(@RequestBody Member member) {
		log.info("controller updatePwd "+ member.getPassword() );
		Member update =memberService.findByAccount(member.getUsername());
		update.setPassword(bcryptEncoder.encode(member.getPassword()));
		update.setUpdatetime(LocalDateTime.now());
		memberService.update(update);
		return Result.success("");
		
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@PathVariable(name="id") long id) {
		log.info("controller delete");
		memberService.delete(id);
		return Result.success("");

	}
	
	@GetMapping("/getuserinfo")
	public Result getuUerInfo(Principal principal) {
		return  Result.success(memberService.findByAccount(principal.getName()));
		
	}
	
	
//	--------------------------
//	@PostMapping(value = "/login")
//	@CrossOrigin
//	public Result login() {
//
//		return Result.success(memberService.findlikeMembers(null, null));
//
//	}

//	@PostMapping(value = "/logout")
//	@CrossOrigin
//	public Result logout() {
//		System.out.println("logout");
//		// List<Member> allList =memberService.findAll();
//		return new Result(200, "登出", "");
//
//	}

//	@GetMapping("/find/{id}")
//	public ResponseEntity<Member> getMemberbyId(@PathParam("id") long id) {
//		Member member = memberService.finMemberbyid(id);
//		return new ResponseEntity<>(member, HttpStatus.OK);
//
//	}


}
