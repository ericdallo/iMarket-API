package br.com.imarket.login;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	@Autowired
	private RememberMeAuthenticationProvider rememberMeProvider;
	
	@GetJson("/")
	public String index() {
		return "API";
	}
	
	@GetJson("/logged")
	public BuyerLoginDTO login() {
		return loggedUser.getBuyer()
						.map(buyer -> loginConverter.convert(buyer))
						.orElseThrow(BuyerNotFoundException::new);
	}
	
	@PostJson("/register")
	public BuyerLoginDTO register(@Valid @RequestBody BuyerRegisterDTO dto) {
		Optional<LoginInfo> foundLoginInfo = loginInfoRepository.findByEmail(dto.getEmail());
		if (foundLoginInfo.isPresent()) {
			LoginInfo loginInfo = foundLoginInfo.get();
			if (dto.getLoginOrigin().isSocial()) {
				loginInfo.setEmail(dto.getEmail());
				loginInfo.setLoginOrigin(dto.getLoginOrigin());
				loginInfo.setPassword(dto.getPassword());
				loginInfoRepository.save(loginInfo);
				return loginIntoSecurity(loginInfo);
			} else {
				throw new EmailAlreadyInUseException();
			}
		}
		
		LoginInfo loginInfo = new LoginInfo(dto.getEmail(), dto.getPassword(), dto.getLoginOrigin(), LoginType.BUYER);
		Buyer buyer = new Buyer(dto.getName(), loginInfo);
		buyerRepository.save(buyer);
		
		return loginIntoSecurity(loginInfo);
	}
	
	private BuyerLoginDTO loginIntoSecurity(LoginInfo loginInfo) {
		RememberMeAuthenticationToken token = new RememberMeAuthenticationToken(rememberMeProvider.getKey(), loginInfo, loginInfo.getAuthorities());
		Authentication authenticatedUser = rememberMeProvider.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		
        return loginConverter.convert(loggedUser.getBuyer().get());
	}
}
