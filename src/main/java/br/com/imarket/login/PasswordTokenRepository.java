package br.com.imarket.login;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = PasswordToken.class, idClass = Long.class)
public interface PasswordTokenRepository {

	void save(PasswordToken passwordToken);

}
