package br.com.imarket.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "PasswordToken not found")
public class PasswordTokenNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7722729695586854469L;
}
