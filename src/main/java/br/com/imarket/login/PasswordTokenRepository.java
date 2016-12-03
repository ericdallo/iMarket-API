package br.com.imarket.login;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = PasswordToken.class, idClass = Long.class)
public interface PasswordTokenRepository {

	void save(PasswordToken passwordToken);

	Optional<PasswordToken> findByLoginInfoAndToken(LoginInfo loginInfo, String token);

	void delete(PasswordToken passwordToken);
}
