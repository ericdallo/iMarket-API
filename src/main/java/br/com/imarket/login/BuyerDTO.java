package br.com.imarket.login;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BuyerDTO {

	@NotEmpty(message = "buyer.login.empty.name")
	private String name;

	@Email(message = "buyer.login.invalid.email")
	@NotEmpty(message = "buyer.login.invalid.email")
	private String email;

	@NotEmpty(message = "buyer.login.empty.password")
	@Length(min = 6, message = "buyer.login.invalid.password")
	private String password;

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
}
