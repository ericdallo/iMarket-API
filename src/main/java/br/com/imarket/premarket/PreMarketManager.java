package br.com.imarket.premarket;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.imarket.market.Market;
import br.com.imarket.market.MarketCreateService;

@Service
public class PreMarketManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreMarketManager.class);
	
	private final PreMarketDTOToPreMarketConverter converter;
	private final PreMarketRepository preMarketRepository;
	private final MarketCreateService marketCreateService;

	@Autowired
	PreMarketManager(PreMarketDTOToPreMarketConverter converter, PreMarketRepository preMarketRepository, MarketCreateService marketCreateService) {
		this.converter = converter;
		this.preMarketRepository = preMarketRepository;
		this.marketCreateService = marketCreateService;
	}

	public void create(PreMarketDTO dto, PreMarketCreateCallback callback) {
		Optional<PreMarket> foundPreMarket = preMarketRepository.findByCnpj(dto.getCnpj());
		if (foundPreMarket.isPresent()) {
			
			if (foundPreMarket.get().isApproved()) {
				LOGGER.info("PreMarket with cnpj: {} already exists", dto.getCnpj());
				callback.alreadyExists();				
			} else {
				callback.waitingApproval();
			}
			return;
		}
		PreMarket preMarketToSave = converter.convert(dto);
		
		preMarketRepository.save(preMarketToSave);
	}

	public ResponseEntity<?> change(PreMarketChange preMarketChange, PreMarketChangeCallback callback) {
		PreMarket preMarket = preMarketRepository.findById(preMarketChange.getId()).orElseThrow(PreMarketNotFoundException::new);
		
		if (preMarketChange.isApproved()) {
			Market market = marketCreateService.create(preMarket);
			preMarket.approves();
			preMarketRepository.save(preMarket);
			return callback.success(market);
		}
		
		preMarket.disapproves(preMarketChange.getDisapprovedText());
		preMarketRepository.save(preMarket);
		return callback.disapproved(preMarket);
	}

}
