package br.com.imarket.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.imarket.buyer.Buyer;
import br.com.imarket.buyer.BuyerRepository;
import br.com.imarket.market.Market;
import br.com.imarket.market.MarketRepository;

@Component
public class LoggedUser {
	
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private MarketRepository marketRepository;
	
	public Optional<Buyer> getBuyer() {
		LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (loginInfo.getLoginType() == LoginType.BUYER) { // TODO unecessary if maybe
			return buyerRepository.findByLoginInfo(loginInfo);
		}
		return Optional.empty();
	}

	public Optional<Market> getMarket() {
		LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (loginInfo.getLoginType() == LoginType.MARKET) { // TODO unecessary if maybe
			return marketRepository.findByLoginInfo(loginInfo);
		}
		return Optional.empty();
	}
	
	public boolean isBuyer() {
		LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return loginInfo.getLoginType() == LoginType.BUYER;
	}

	public boolean isMarket() {
		LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return loginInfo.getLoginType() == LoginType.MARKET;
	}


}
