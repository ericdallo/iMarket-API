package br.com.imarket.buyer;

import static java.util.Arrays.asList;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import br.com.imarket.login.LoginOrigin;

@Entity
@Table(name = "buyer")
public class Buyer implements UserDetails {
	
	private static final long serialVersionUID = 6390327706542774402L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = false, length = 60)
	private String name;
	
	@Email
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "login_origin", nullable = false)
	private LoginOrigin loginOrigin;

	public Buyer(String name, String email, String password, LoginOrigin loginOrigin) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.loginOrigin = loginOrigin;
	}
	
	@Deprecated
	Buyer() {} //Hibernate eyes only
	
	@PrePersist
	@PreUpdate
	void hashPassword() {
		this.password = password != null ? BCrypt.hashpw(password, BCrypt.gensalt()) : password;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LoginOrigin getLoginOrigin() {
		return loginOrigin;
	}

	public void setLoginOrigin(LoginOrigin loginOrigin) {
		this.loginOrigin = loginOrigin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return asList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
