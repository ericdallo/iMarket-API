package br.com.imarket.market;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.imarket.login.BuyerLoginDTO;

@Component
public class MarketToMarketLoginDTOConverter implements Converter<Market, BuyerLoginDTO> {

	@Override
	public BuyerLoginDTO convert(Market from) {
		
		return null;
	}

}