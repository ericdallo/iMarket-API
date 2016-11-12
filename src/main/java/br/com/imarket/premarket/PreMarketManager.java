package br.com.imarket.premarket;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreMarketManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreMarketManager.class);
	
	@Autowired
	private PreMarketDTOToPreMarketConverter converter;
	@Autowired
	private PreMarketRepository preMarketRepository;

	public void create(PreMarketDTO dto, PreMarketCallback callback) {
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

}
