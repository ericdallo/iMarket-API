package br.com.imarket.login;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.composed.web.Post;
import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.imarket.buyer.Buyer;
import br.com.imarket.buyer.BuyerRepository;
import br.com.imarket.buyer.BuyerToBuyerLoginDTOConverter;
import br.com.imarket.exception.EmailAlreadyInUseException;

@RestController
public class BuyerLoginController {
	
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private LoggedBuyer loggedBuyer;
	@Autowired
	private BuyerToBuyerLoginDTOConverter loginConverter;

	@GetJson("/api/home")
	public String indexjson() {
		return "homeApi";
	}
	
	@GetJson("/")
	public String index() {
		return "home";
	}
	
	@GetJson("/logged")
	public BuyerLoginDTO login() {
		return loginConverter.convert(loggedBuyer.get());
	}
	
	@Post("/register")
	@ResponseStatus(CREATED)
	public void register(@Valid @RequestBody BuyerRegisterDTO dto) {
		Optional<Buyer> foundBuyer = buyerRepository.findByEmail(dto.getEmail());
		if (foundBuyer.isPresent()) {
			Buyer buyer = foundBuyer.get();
			if (buyer.getLoginOrigin().isSocial()) {
				buyer.setPassword(dto.getPassword());
				buyer.setEmail(dto.getEmail());
				buyer.setName(dto.getName());
				buyerRepository.save(buyer);
				return;
			}
			throw new EmailAlreadyInUseException();
		}
		
		Buyer buyer = new Buyer(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getLoginOrigin());
		buyerRepository.save(buyer);
	}
}
