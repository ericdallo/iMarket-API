package br.com.imarket.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.AuthCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
class CloudConfiguration {
	
	@Value("${cloud.auth.json.name}")
	private String gcloudJsonAuthName;
	
	@Bean
	AuthCredentials authCredentials() throws IOException  {
		ClassLoader classLoader = getClass().getClassLoader();
		return AuthCredentials.createForJson(classLoader.getResourceAsStream(gcloudJsonAuthName));
	}

	@Bean
	Storage storage(AuthCredentials authCredentials) {
		return StorageOptions.newBuilder()
							 .setAuthCredentials(authCredentials)
							 .build()
							 .getService();
	}
}
