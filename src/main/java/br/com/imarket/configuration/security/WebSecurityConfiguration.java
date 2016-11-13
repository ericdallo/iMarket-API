package br.com.imarket.configuration.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	private LoginFailureHandler failureLogin;
	@Autowired
	private LoginSuccessHandler successHandler;
	@Autowired
	private LogoutHandler logoutSuccessHandler;

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
        	.csrf().disable()
            .authorizeRequests()
            	.antMatchers(OPTIONS,"/**").permitAll()
                .antMatchers("/", "/favicon.ico").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers(POST, AllowedEndpoint.listBy(POST)).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.loginPage("/login")
	            .loginProcessingUrl("/login")
	            .successHandler(successHandler)
	            .failureHandler(failureLogin)
	            .permitAll()
	            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/login", DELETE.name()))
            	.clearAuthentication(true)
                .deleteCookies(cookieName)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
            .exceptionHandling()
            	.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            	.and()
            .rememberMe().rememberMeServices(rememberMeServices());
    }

	private CorsFilter corsFilter() {
		return new CorsFilter();
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
	TokenBasedRememberMeService rememberMeServices() {
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