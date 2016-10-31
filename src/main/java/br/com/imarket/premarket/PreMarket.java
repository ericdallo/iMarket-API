package br.com.imarket.premarket;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "pre_market")
public class PreMarket {
	
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Column(name = "name", nullable = false)
	private String name;
	
	@CNPJ
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	
	@Email
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotNull
	@Embedded
	private MarketAddress address;
	
	@Column(name = "has_delivery", nullable = false)
	private boolean hasDelivery;

	@Column(name = "disapproved_text")
	private String disapprovedText;
	
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MarketAddress getAddress() {
		return address;
	}

	public void setAddress(MarketAddress address) {
		this.address = address;
	}

	public boolean isHasDelivery() {
		return hasDelivery;
	}

	public void setHasDelivery(boolean hasDelivery) {
		this.hasDelivery = hasDelivery;
	}

	public void disapproves(String disapprovedText) {
		this.disapprovedText = disapprovedText;
	}
	
	public String getDisapprovedText() {
		return disapprovedText;
	}
}
