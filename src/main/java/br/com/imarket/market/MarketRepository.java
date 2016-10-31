package br.com.imarket.market;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Market.class, idClass = Long.class)
public interface MarketRepository {

	Market save(Market market);

}
