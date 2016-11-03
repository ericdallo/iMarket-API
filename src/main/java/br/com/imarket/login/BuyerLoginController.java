package br.com.imarket.login;

import static br.com.imarket.login.LoginType.BUYER;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.imarket.buyer.Buyer;
import br.com.imarket.buyer.BuyerRepository;
import br.com.imarket.buyer.BuyerToBuyerLoginDTOConverter;
import br.com.imarket.exception.BuyerNotFoundException;
import br.com.imarket.exception.EmailAlreadyInUseException;

@RestController
public class BuyerLoginController {
	
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private BuyerToBuyerLoginDTOConverter loginConverter;
	@Autowired
	private LoginInfoRepository loginInfoRepository;

	@GetJson("/")
	public String index() {
		return "home";
	}
	
	@GetJson("/logged")
	public BuyerLoginDTO login() {
		return loggedUser.getBuyer()
						.map(buyer -> loginConverter.convert(buyer))
						.orElseThrow(BuyerNotFoundException::new);
	}
	
	@PostJson("/register")
	public void register(@Valid @RequestBody BuyerRegisterDTO dto) {
		Optional<LoginInfo> foundLoginInfo = loginInfoRepository.findByEmail(dto.getEmail());
		if (foundLoginInfo.isPresent()) {
			LoginInfo loginInfo = foundLoginInfo.get();
			if (dto.getLoginOrigin().isSocial() && loginInfo.getLoginType() == BUYER) {
				loginInfo.setEmail(dto.getEmail());
				loginInfo.setLoginOrigin(dto.getLoginOrigin());
				loginInfo.setPassword(dto.getPassword());
				loginInfo.disablePasswordHash();
				loginInfoRepository.save(loginInfo);
				return;
			}
			throw new EmailAlreadyInUseException();
		}
		
		LoginInfo loginInfo = new LoginInfo(dto.getEmail(), dto.getPassword(), dto.getLoginOrigin(), LoginType.BUYER);
		if (dto.getLoginOrigin().isSocial()) {
			loginInfo.disablePasswordHash();
		}
		Buyer buyer = new Buyer(dto.getName(), loginInfo);
		buyerRepository.save(buyer);
	}
}
