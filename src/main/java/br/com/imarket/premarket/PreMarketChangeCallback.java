package br.com.imarket.premarket;

import org.springframework.http.ResponseEntity;

import br.com.imarket.market.Market;

public interface PreMarketChangeCallback {

	ResponseEntity<?> success(Market market);

	ResponseEntity<?> disapproved(PreMarket preMarket);

}
