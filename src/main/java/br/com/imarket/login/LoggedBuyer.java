package br.com.imarket.login;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.imarket.user.Buyer;

@Component
public class LoggedBuyer {
	
	public Buyer get() {
		return (Buyer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
