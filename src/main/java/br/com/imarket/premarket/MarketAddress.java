package br.com.imarket.premarket;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class MarketAddress {

	@NotBlank
	@Length(max = 7)
	@Column(name = "cep")
	private String cep;
	
	@Length(max = 2)
	@NotBlank
	@Column(name = "state")
	private String state;
	
	@NotBlank
	@Column(name = "city")
	private String city;
	
	@NotBlank
	@Column(name = "address")
	private String address;
	
	@NotBlank
	@Column(name = "number")
	private String number;
	
	@NotBlank
	@Column(name = "neighborhood")
	private String neighborhood;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
}
