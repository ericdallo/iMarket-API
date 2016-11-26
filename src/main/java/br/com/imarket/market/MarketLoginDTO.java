package br.com.imarket.market;

import br.com.imarket.login.LoginType;

public class MarketLoginDTO {

	private Long id;
	private String name;
	private LoginType loginType;
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LoginType getLoginType() {
		return loginType;
	}
	
	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
