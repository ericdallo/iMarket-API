package br.com.imarket.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.imarket.login.LoginInfoRepository;

@Repository
public class UserDetailsDAO implements UserDetailsService {
	
	@Autowired
	private LoginInfoRepository loginInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loginInfoRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("user not found"));
	}

}
