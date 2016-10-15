package br.com.imarket.configuration.security;

import static org.springframework.http.HttpMethod.DELETE;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class LoggoutRequestMatcher implements RequestMatcher {

	@Override
	public boolean matches(HttpServletRequest request) {
		return DELETE.name().equals(request.getMethod());
	}

}
