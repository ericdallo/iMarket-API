package br.com.imarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "buyer not found")
public class BuyerNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 4821373747379230401L;
}
