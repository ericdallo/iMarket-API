package br.com.imarket.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7568955935047038004L;
}
