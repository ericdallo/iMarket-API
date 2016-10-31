package br.com.imarket.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import br.com.imarket.premarket.PreMarket;

@Service
public class MarketService {

	private static final int PASSWORD_LENGTH = 8;
	
	@Autowired
	private MarketRepository marketRepository;

	public Market create(PreMarket preMarket) {
		Market market = new Market().from(preMarket);
		
		String generatedPassword = new RandomValueStringGenerator(PASSWORD_LENGTH).generate();
		market.setPassword(generatedPassword);
		
		return marketRepository.save(market);
	}

}
