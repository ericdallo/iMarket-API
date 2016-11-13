package br.com.imarket.premarket;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.imarket.market.picture.MarketPicture;

public class PreMarketDTOToPreMarketConverterTest {
	
	private PreMarketDTOToPreMarketConverter subject;

	@Test
	public void shouldConvertAllFields() {
		subject = new PreMarketDTOToPreMarketConverter();
		
		PreMarketDTO from = new PreMarketDTO();
		MarketAddress address = new MarketAddress();
		String cnpj = "63.077.469/0001-49";
		String email = "any@email.com";
		boolean hasDelivery = true;
		String name = "MarketTest";
		MarketPicture picture = new MarketPicture("anyFile");
		
		from.setAddress(address);
		from.setCnpj(cnpj);
		from.setEmail(email);
		from.setHasDelivery(hasDelivery);
		from.setName(name);
		from.setPicture(picture);
		
		PreMarket converted = subject.convert(from);
		
		assertEquals(address, converted.getAddress());
		assertEquals(cnpj, converted.getCnpj());
		assertEquals(email, converted.getEmail());
		assertEquals(hasDelivery, converted.hasDelivery());
		assertEquals(name, converted.getName());
		assertEquals(picture, converted.getPicture());
	}

}
