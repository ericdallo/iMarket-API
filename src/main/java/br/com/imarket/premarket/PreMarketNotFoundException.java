package br.com.imarket.premarket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "PreMarket not found")
public class PreMarketNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -1206186071392693832L;
}