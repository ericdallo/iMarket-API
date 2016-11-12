package br.com.imarket.premarket;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PreMarketDTOToPreMarketConverter implements Converter<PreMarketDTO, PreMarket> {

	@Override
	public PreMarket convert(PreMarketDTO from) {
		PreMarket to = new PreMarket();
		to.setEmail(from.getEmail());
		to.setName(from.getName());
		to.setCnpj(from.getCnpj());
		to.setPicture(from.getPicture());
		to.setAddress(from.getAddress());
		to.setHasDelivery(from.hasDelivery());
		return to;
	}

}
