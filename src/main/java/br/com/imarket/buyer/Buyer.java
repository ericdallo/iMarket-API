package br.com.imarket.buyer;

import static javax.persistence.CascadeType.ALL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.imarket.login.LoginInfo;

@Entity
@Table(name = "buyer")
public class Buyer {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = false, length = 60)
	private String name;
	
	@OneToOne(cascade = ALL)
	@JoinColumn(name = "login_info_id", referencedColumnName = "id")
	private LoginInfo loginInfo;
	
	@Deprecated
	Buyer() {} //Hibernate eyes only
	
	public Buyer(String name, LoginInfo loginInfo) {
		this.name = name;
		this.loginInfo = loginInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
	
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public String getEmail() {
		return loginInfo.getEmail();
	}
	
}
