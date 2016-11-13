package br.com.imarket.configuration.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
class AdminRequestMatcher implements RequestMatcher {

	@Override
	public boolean matches(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (token != null && token.length() > "Basic ".length()) {
			return true;
		}
		return false;
	}

}
