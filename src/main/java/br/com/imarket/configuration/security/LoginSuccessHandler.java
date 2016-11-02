package br.com.imarket.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.imarket.buyer.BuyerToBuyerLoginDTOConverter;
import br.com.imarket.login.LoggedUser;
import br.com.imarket.market.MarketToMarketLoginDTOConverter;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private static final String IS_MOBILE = "is_mobile";
	
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private BuyerToBuyerLoginDTOConverter buyerLoginConverter;
	@Autowired
	private MarketToMarketLoginDTOConverter marketLoginConverter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    	boolean isMobile = httpServletRequest.getHeader(IS_MOBILE) != null;
    	
    	ObjectMapper objMapper = new ObjectMapper();
    	
    	if (isMobile) {
    		if (loggedUser.isBuyer()) {
        		String jsonString = objMapper.writeValueAsString(buyerLoginConverter.convert(loggedUser.getBuyer().get()));
        		httpServletResponse.getWriter().write(jsonString);
        		httpServletResponse.getWriter().flush();
        		httpServletResponse.getWriter().close();
        	} else {
        		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        		return;
        	}
    	} else if (loggedUser.isMarket()) {
    		String jsonString = objMapper.writeValueAsString(marketLoginConverter.convert(loggedUser.getMarket().get()));
    		httpServletResponse.getWriter().write(jsonString);
    		httpServletResponse.getWriter().flush();
    		httpServletResponse.getWriter().close();
    	}
    	
    	httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    	httpServletResponse.setContentType(MediaType.APPLICATION_JSON.getType());
    }

}
