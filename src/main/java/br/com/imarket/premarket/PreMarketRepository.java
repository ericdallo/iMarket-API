package br.com.imarket.premarket;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = PreMarket.class, idClass = Long.class)
public interface PreMarketRepository {

	void save(PreMarket preMarket);
	
}
