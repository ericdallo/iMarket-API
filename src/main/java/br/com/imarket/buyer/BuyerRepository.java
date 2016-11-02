package br.com.imarket.buyer;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import br.com.imarket.login.LoginInfo;

@RepositoryDefinition(domainClass = Buyer.class, idClass = Long.class)
public interface BuyerRepository {

	void save(Buyer buyer);

	Optional<Buyer> findByLoginInfo(LoginInfo loginInfo);
}
