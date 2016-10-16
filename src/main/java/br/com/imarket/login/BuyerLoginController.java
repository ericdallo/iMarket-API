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

import br.com.imarket.buyer.BuyerToBuyerLoginDTOConverter;
import br.com.imarket.exception.EmailAlreadyInUseException;
import br.com.imarket.user.Buyer;
import br.com.imarket.user.BuyerRepository;

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
	public void register(@Valid @RequestBody BuyerDTO dto) throws EmailAlreadyInUseException {
		Optional<Buyer> foundBuyer = buyerRepository.findByEmail(dto.getEmail());
		if (foundBuyer.isPresent()) {
			throw new EmailAlreadyInUseException();
		}
		
		Buyer buyer = new Buyer(dto.getName(), dto.getEmail(), dto.getPassword());
		buyerRepository.save(buyer);
	}
}
