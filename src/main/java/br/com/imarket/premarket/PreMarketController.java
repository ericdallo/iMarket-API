package br.com.imarket.premarket;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.imarket.market.Market;
import br.com.imarket.market.MarketService;

@RestController
public class PreMarketController {

	@Autowired
	private PreMarketRepository preMarketRepository;
	@Autowired
	private MarketService marketService;

	@PostJson("/premarkets")
	public void create(@Valid @RequestBody PreMarket preMarket) {
		preMarketRepository.save(preMarket);
	}
	
	@GetJson("/api/premarkets")
	public List<PreMarket> list() {
		return preMarketRepository.findAll();
	}
	
	@PostJson("/api/premarkets/{id}")
	public Market createMarket(@Valid @RequestBody PreMarketChange preMarketChange) {
		PreMarket preMarket = preMarketRepository.findById(preMarketChange.getId()).orElseThrow(PreMarketNotFoundException::new);
		
		if (preMarketChange.isApproved()) {
			Market market = marketService.create(preMarket);
			return market;
		}
		
		preMarket.disapproves(preMarketChange.getDisapprovedText());
		preMarketRepository.save(preMarket);
		
		return null;
	}
}
