package com.rg.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rg.entity.Member;

public interface MemberService {
	
	public Page<Member> findlikeMembers(String account,Pageable pageable);
	
	public Member addMember(Member member);
	
	public Member finMemberbyid(long id);
	
	public void delete (long id);
	
	public Member update(Member member) ;
	
	public Member findByAccount(String account) ;


}
