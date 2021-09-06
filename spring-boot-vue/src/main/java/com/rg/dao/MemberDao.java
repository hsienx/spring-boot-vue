package com.rg.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rg.entity.Member;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {

	public Optional<Member> findByUsername(String username);

	public Optional<Member> findAllById(long id);

	@Query(value = "select * from Member m WHERE "
		+ " (?1 is NULL OR m.member_account LIKE CONCAT(CONCAT('%',?1),'%'))", nativeQuery = true)
	Page<Member> likeAccName(String username, Pageable pageable);
	// 因為在Oracle中CONCAT函數每次只能進行兩個字符串的拼接，兩個以上就會報錯

}
