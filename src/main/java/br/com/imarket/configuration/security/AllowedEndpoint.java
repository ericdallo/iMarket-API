package br.com.imarket.configuration.security;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.http.HttpMethod;

public enum AllowedEndpoint {
	
	CREATE_PREMARKET(POST, "/premarkets"),
	CREATE_PREMARKET_PICTURE(POST, "/premarkets/picture"),
	;

	private final HttpMethod method;
	private final String endpoint;
	
	private AllowedEndpoint(HttpMethod method, String endpoint) {
		this.method = method;
		this.endpoint = endpoint;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public static String[] listBy(HttpMethod method) {
		return stream(values())
						.filter(allowed -> allowed.getMethod() == method)
						.map(allowed -> allowed.getEndpoint())
						.toArray(size -> new String[size]);
	}
}
