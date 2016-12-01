package br.com.imarket.login;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "password_token")
public class PasswordToken {

    @Id
    @GeneratedValue
    private Long id;
 
    @Column(name = "token")
    private String token;
 
    @OneToOne
    @JoinColumn(nullable = false, name = "login_info_id", referencedColumnName = "id")
    private LoginInfo loginInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public static PasswordToken from(LoginInfo loginInfo) {
		PasswordToken passwordToken = new PasswordToken();
		passwordToken.setLoginInfo(loginInfo);
		passwordToken.setToken(UUID.randomUUID().toString());
		return passwordToken;
	}
 
}
