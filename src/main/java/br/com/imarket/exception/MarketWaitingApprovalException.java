package br.com.imarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED, reason = "Market waiting approval")
public class MarketWaitingApprovalException extends RuntimeException {
	private static final long serialVersionUID = 8507095344728932906L;
}
