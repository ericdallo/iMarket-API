package br.com.imarket.login;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuyerRegisterDTO {

	@NotEmpty(message = "buyer.login.empty.name")
	private String name;

	@Email(message = "buyer.login.invalid.email")
	@NotEmpty(message = "buyer.login.invalid.email")
	private String email;

	@NotEmpty(message = "buyer.login.empty.password")
	@Length(min = 6, message = "buyer.login.invalid.password")
	private String password;
	
	@NotNull(message = "buyer.login.empty.origin")
	@JsonProperty("login_origin")
	private LoginOrigin loginOrigin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginOrigin getLoginOrigin() {
		return loginOrigin;
	}

	public void setLoginOrigin(LoginOrigin loginOrigin) {
		this.loginOrigin = loginOrigin;
	}
}
