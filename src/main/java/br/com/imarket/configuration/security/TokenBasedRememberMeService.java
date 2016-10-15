package br.com.imarket.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class TokenBasedRememberMeService extends TokenBasedRememberMeServices {

	private static final String CREDENTIALS = "credentials";

	public TokenBasedRememberMeService(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	/**
	 * Locates the Spring Security remember me token in the request and returns its value.
	 *
	 * @param request the submitted request which is to be authenticated
	 * @return the value of the request header (which was originally provided by the cookie - API expects it in header)
	 */
	@Override
	protected String extractRememberMeCookie(HttpServletRequest request) {
		String token = request.getHeader(CREDENTIALS);
		if ((token == null) || (token.length() == 0)) {
			return null;
		}

		return token;
	}
	
}
