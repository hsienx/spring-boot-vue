package com.rg.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name="MEMBER")
public class Member {
	//會員資料
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="MEMBER_ID")
	private Long id ;
	
	@Column(name="MEMBER_ACCOUNT")
	private String username;
	
	@Column(name="MEMBER_PASSWORD")
	private String password;
	
	@Column(name="MEMBER_NAME")
	private String cname;
	
	@Column(name="MEMBER_TEL")
	private String tel;
	
	@Column(name="MEMBER_ADD")
	private String address;
	
	@Column(name="MEMBER_STATUS")
	private String status;
	
	@Column(name="UPDATE_TIME")
	private LocalDateTime updatetime;




	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}
	
	
	

}
