package br.com.imarket.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.imarket.buyer.BuyerToBuyerLoginDTOConverter;
import br.com.imarket.login.LoggedBuyer;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private LoggedBuyer loggedBuyer;
	@Autowired
	private BuyerToBuyerLoginDTOConverter loginConverter;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    	httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    	httpServletResponse.setContentType("application/json");
    	
    	ObjectMapper objMapper = new ObjectMapper();
    	String jsonString = objMapper.writeValueAsString(loginConverter.convert(loggedBuyer.get()));
    	
    	httpServletResponse.getWriter().write(jsonString);
    	httpServletResponse.getWriter().flush();
    	httpServletResponse.getWriter().close();
    }

}
