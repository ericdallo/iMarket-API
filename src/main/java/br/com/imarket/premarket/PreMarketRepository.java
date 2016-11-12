package br.com.imarket.premarket;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = PreMarket.class, idClass = Long.class)
public interface PreMarketRepository {

	void save(PreMarket preMarket);

	List<PreMarket> findAll();

	Optional<PreMarket> findById(Long id);

	Optional<PreMarket> findByCnpj(String cnpj);
	
}
