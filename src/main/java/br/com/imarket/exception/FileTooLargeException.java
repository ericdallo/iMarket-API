package br.com.imarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE, reason = "Invalid file size")
public class FileTooLargeException extends RuntimeException {
	private static final long serialVersionUID = -6710085424450971670L;
}
