package br.com.imarket.login;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = LoginInfo.class, idClass = Long.class)
public interface LoginInfoRepository {

	Optional<LoginInfo> findByEmail(String email);
	
	void save(LoginInfo loginInfo);
}
