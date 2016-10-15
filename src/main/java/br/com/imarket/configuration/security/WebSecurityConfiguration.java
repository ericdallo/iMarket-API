package br.com.imarket.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

@EnableWebSecurity
@Order(2)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("${security.cookie.name}")
	private String cookieName;
	
	@Autowired 
	private UserDetailsDAO userDetailsService;
	@Autowired 
	private RememberMeAuthenticationProvider rememberMeAuthenticationProvider;
	@Autowired
	private LoggoutRequestMatcher logoutRequestMatcher;
	@Autowired
	private LoginFailureHandler failureLogin;
	@Autowired
	private LoginSuccessHandler successLogin;

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.loginPage("/login")
	            .loginProcessingUrl("/login")
	            .successHandler(successLogin)
	            .failureHandler(failureLogin)
	            .permitAll()
	            .and()
            .logout()                           
                .logoutUrl("/login")
                .logoutRequestMatcher(logoutRequestMatcher)
                .deleteCookies(cookieName)
                .and()
            .exceptionHandling()
            	.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            	.and()
            .rememberMe().rememberMeServices(rememberMeServices());
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		auth.authenticationProvider(rememberMeAuthenticationProvider);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean   
	AbstractRememberMeServices rememberMeServices() {
		TokenBasedRememberMeService service = new TokenBasedRememberMeService(cookieName, userDetailsService);
		service.setAlwaysRemember(true);
		service.setCookieName(cookieName);
		return service;
	}

	@Bean
	RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new RememberMeAuthenticationProvider(cookieName);
	}
}