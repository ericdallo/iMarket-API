package br.com.imarket.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import br.com.imarket.login.PasswordToken;
import br.com.imarket.login.PasswordTokenRepository;
import br.com.imarket.premarket.PreMarket;

@Service
public class MarketCreateService {

	private static final int PASSWORD_LENGTH = 8;
	
	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	public Market create(PreMarket preMarket) {
		String generatedPassword = new RandomValueStringGenerator(PASSWORD_LENGTH).generate();
		
		Market market = new Market().from(preMarket, generatedPassword);
		PasswordToken passwordToken = PasswordToken.from(market.getLoginInfo());
		passwordTokenRepository.save(passwordToken);
		
		eventPublisher.publishEvent(new MarketCreatedEvent(market, passwordToken.getToken()));
		
		return marketRepository.save(market);
	}

}
