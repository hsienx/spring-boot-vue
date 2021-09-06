package com.rg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rg.entity.Member;
import com.rg.service.MemberService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		log.info("UserDetailServiceImpl  username = " + username );
		
		Member userMember =memberService.findByAccount(username);
		if(userMember==null) {
			throw  new UsernameNotFoundException("用戶或密碼錯誤");
		}
		return new AccountUser(userMember.getId(),userMember.getUsername(),userMember.getPassword(),null);
	}

	
}
