package br.com.imarket.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@EnableWebSecurity
@Order(1)
class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("${admin.username}")
	private String adminUsername;
	@Value("${admin.password}")
	private String adminPassword;
	@Autowired
	private AdminRequestMatcher adminRequestMatcher;

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.requestMatcher(adminRequestMatcher)
        	.authorizeRequests()
        		.anyRequest().authenticated().and()
	        .httpBasic().and()
		        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		        .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
          withUser(adminUsername).password(adminPassword).roles("ADMIN");
    }
}