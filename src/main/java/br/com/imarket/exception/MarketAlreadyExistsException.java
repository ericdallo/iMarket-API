package br.com.imarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Market already exists")
public class MarketAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 900200503246866399L;
}
