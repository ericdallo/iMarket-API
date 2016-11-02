package br.com.imarket.market;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import br.com.imarket.login.LoginInfo;

@RepositoryDefinition(domainClass = Market.class, idClass = Long.class)
public interface MarketRepository {

	Market save(Market market);

	Optional<Market> findByLoginInfo(LoginInfo loginInfo);

}
