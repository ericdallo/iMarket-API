package br.com.imarket.market;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MarketToMarketLoginDTOConverter implements Converter<Market, MarketLoginDTO> {

	@Override
	public MarketLoginDTO convert(Market from) {
		MarketLoginDTO to = new MarketLoginDTO();
		to.setId(from.getId());
		to.setName(from.getName());
		to.setLoginType(from.getLoginInfo().getLoginType());
		to.setUrl(from.getUrl());
		return to;
	}

}