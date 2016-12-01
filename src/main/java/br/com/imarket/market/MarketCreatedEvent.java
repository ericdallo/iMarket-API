package br.com.imarket.market;

public class MarketCreatedEvent {

	private final Market market;
	private final String passwordToken;

	public MarketCreatedEvent(Market market, String passwordToken) {
		this.market = market;
		this.passwordToken = passwordToken;
	}

	public Market getMarket() {
		return market;
	}
	
	public String getPasswordToken() {
		return passwordToken;
	}

}