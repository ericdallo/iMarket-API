package br.com.imarket.premarket;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreMarketController {

	@Autowired
	private PreMarketRepository preMarketRepository;

	@PostJson("/premarkets")
	public void create(@Valid @RequestBody PreMarket preMarket) {
		preMarketRepository.save(preMarket);
	}
}
