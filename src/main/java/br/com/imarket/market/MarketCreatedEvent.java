package br.com.imarket.market;

public class MarketCreatedEvent {
	
	private final Market market;
	
	public MarketCreatedEvent(Market market) {
		this.market = market;
	}

	public Market getMarket() {
		return market;
	}
}
