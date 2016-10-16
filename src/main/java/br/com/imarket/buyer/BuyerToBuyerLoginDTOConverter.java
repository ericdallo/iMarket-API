package br.com.imarket.buyer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.imarket.login.BuyerLoginDTO;
import br.com.imarket.user.Buyer;

@Component
public class BuyerToBuyerLoginDTOConverter implements Converter<Buyer, BuyerLoginDTO> {

	@Override
	public BuyerLoginDTO convert(Buyer from) {
		BuyerLoginDTO to = new BuyerLoginDTO();
		to.setName(from.getName());
		to.setEmail(from.getEmail());
		return to;
	}

}
