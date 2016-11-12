package br.com.imarket.premarket;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.imarket.market.picture.MarketPicture;

public class PreMarketDTO {

	@NotBlank
	@JsonProperty("name")
	private String name;
	
	@NotBlank
	@CNPJ
	@JsonProperty("cnpj")
	private String cnpj;
	
	@NotBlank
	@Email
	@JsonProperty("email")
	private String email;
	
	@NotNull
	@JsonProperty("address")
	private MarketAddress address;
	
	@JsonProperty("has_delivery")
	private boolean hasDelivery;

	@JsonProperty("picture")
	private MarketPicture picture;

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

	public boolean hasDelivery() {
		return hasDelivery;
	}

	public void setHasDelivery(boolean hasDelivery) {
		this.hasDelivery = hasDelivery;
	}

	public MarketPicture getPicture() {
		return picture;
	}

	public void setPicture(MarketPicture picture) {
		this.picture = picture;
	}
	
}
