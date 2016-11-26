package br.com.imarket.util;

import static org.springframework.util.StringUtils.delete;

import org.springframework.util.StringUtils;

public class UrlUtils {

	public static String toUrl(String name) {
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		String url = name.replace(' ', '-');
		url = delete(url, "'");
		
		return url.toLowerCase();
	}

}
