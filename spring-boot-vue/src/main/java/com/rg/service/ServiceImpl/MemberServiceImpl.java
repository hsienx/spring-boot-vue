package com.rg.service.ServiceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.rg.dao.MemberDao;
import com.rg.entity.Member;
import com.rg.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDao memberDao;
	
	public Page<Member> findlikeMembers(String username,Pageable pageable) {
		return memberDao.likeAccName(username,pageable);
	}
	
	public Member findByAccount(String username){
		return memberDao.findByUsername(username).orElse(new Member());
	}
	
	public Member addMember(Member member) {
		return memberDao.save(member);
	}
	
	public Member update(Member member) {
		return memberDao.save(member);
	}
	
	public Member finMemberbyid(long id){
		return memberDao.findAllById(id).orElse(new Member());
		
	}
	
	public void delete (long id) {
		memberDao.deleteById(id);
	}




}
