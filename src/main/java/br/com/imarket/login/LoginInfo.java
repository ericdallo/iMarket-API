package br.com.imarket.login;

import static java.util.Arrays.asList;
import static javax.persistence.EnumType.STRING;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "login_info")
public class LoginInfo implements UserDetails {
	
	private static final long serialVersionUID = -129062516764514506L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Email
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Enumerated(STRING)
	@Column(name = "login_origin", nullable = false)
	private LoginOrigin loginOrigin;
	
	@Enumerated(STRING)
	@Column(name = "login_type", nullable = false)
	private LoginType loginType;

	@Transient
	private boolean shouldHash = true;
	
	@Deprecated //Hibernate eyes only
	LoginInfo(){}
	
	public LoginInfo(String email, String password, LoginOrigin loginOrigin, LoginType loginType) {
		this.email = email;
		this.password = password;
		this.loginOrigin = loginOrigin;
		this.loginType = loginType;
	}

	@PrePersist
	@PreUpdate
	void hashPassword() {
		this.password = password != null && shouldHash ? BCrypt.hashpw(password, BCrypt.gensalt()) : password;
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
	
	public LoginType getLoginType() {
		return loginType;
	}
	
	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
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

	public boolean isSocial() {
		return loginOrigin.isSocial();
	}

	public boolean isBuyer() {
		return loginType == LoginType.BUYER;
	}
	
	public void disablePasswordHash() {
		this.shouldHash = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginInfo other = (LoginInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	
}
