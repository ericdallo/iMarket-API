package br.com.imarket.configuration.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@EnableWebSecurity
@Order(1)
class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.antMatcher("/api/**").authorizeRequests()
        	.anyRequest().authenticated().and()
	        .httpBasic().and()
		        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		        .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }
}