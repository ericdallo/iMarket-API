package br.com.imarket.buyer;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Buyer.class, idClass = Long.class)
public interface BuyerRepository {

	Optional<Buyer> findByEmail(String email);

	void save(Buyer buyer);
}
