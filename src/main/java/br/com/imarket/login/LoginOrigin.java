package br.com.imarket.login;

public enum LoginOrigin {
	
	IMARKET,
	FACEBOOK,
	;

	public boolean isSocial() {
		return this == FACEBOOK;
	}
}
