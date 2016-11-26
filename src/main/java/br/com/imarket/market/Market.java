package br.com.imarket.market;

import static br.com.imarket.login.LoginOrigin.IMARKET;
import static br.com.imarket.login.LoginType.MARKET;
import static javax.persistence.CascadeType.ALL;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import br.com.imarket.login.LoginInfo;
import br.com.imarket.premarket.MarketAddress;
import br.com.imarket.premarket.PreMarket;
import br.com.imarket.util.UrlUtils;

@Entity
@Table(name = "market")
public class Market {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = false, length = 80)
	private String name;
	
	@OneToOne(cascade = ALL)
	@JoinColumn(name = "login_info_id", referencedColumnName = "id")
	private LoginInfo loginInfo;
	
	@CNPJ
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	
	@NotNull
	@Embedded
	private MarketAddress address;
	
	@NotBlank
	@Column(name = "url", nullable = false, length = 80)
	private String url;
	
	@Column(name = "has_delivery", nullable = false)
	private boolean hasDelivery;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public MarketAddress getAddress() {
		return address;
	}

	public void setAddress(MarketAddress address) {
		this.address = address;
	}

	public boolean hasDelivery() {
		return hasDelivery;
	}

	public void setDelivery(boolean hasDelivery) {
		this.hasDelivery = hasDelivery;
	}
	
	public String getUrl() {
		return url;
	}
	
	public Market from(PreMarket preMarket, String password) {
		this.loginInfo = new LoginInfo(preMarket.getEmail(), password, IMARKET, MARKET);
		this.name = preMarket.getName();
		this.cnpj = preMarket.getCnpj();
		this.hasDelivery = preMarket.isHasDelivery();
		this.address = preMarket.getAddress();
		this.url = UrlUtils.toUrl(preMarket.getName());
		return this;
	}
	
}
