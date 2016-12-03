package br.com.imarket.login;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "LoginInfo not found")
public class LoginInfoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2307998193517519267L;
}
