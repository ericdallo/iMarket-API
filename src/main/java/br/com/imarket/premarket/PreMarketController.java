package br.com.imarket.premarket;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.composed.web.Post;
import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.imarket.configuration.security.Admin;
import br.com.imarket.exception.FileTooLargeException;
import br.com.imarket.exception.MarketAlreadyExistsException;
import br.com.imarket.exception.MarketWaitingApprovalException;
import br.com.imarket.market.Market;

@RestController
public class PreMarketController {

	@Value("${picture.upload.max.size}")
	private String maxSize;
	
	@Autowired
	private PreMarketRepository preMarketRepository;
	@Autowired
	private PictureStorage pictureStorage;
	@Autowired
	private PreMarketManager preMarketManager;

	@PostJson("/premarkets")
	public void create(@Valid @RequestBody PreMarketDTO dto) {
		preMarketManager.create(dto, new PreMarketCreateCallback() {

			@Override
			public void alreadyExists() {
				throw new MarketAlreadyExistsException();
			}

			@Override
			public void waitingApproval() {
				throw new MarketWaitingApprovalException();
			}
			
		});
	}
	
	@Post(value = "/premarkets/picture", consumes = "multipart/form-data")
	public Long savePicture(@RequestParam("file") MultipartFile file) {
		if (file.getSize() > Long.valueOf(maxSize)) {
			throw new FileTooLargeException();
		}
		return pictureStorage.store(file).getId();
	}
	
	@Admin
	@GetJson("/premarkets")
	public List<PreMarket> list() {
		return preMarketRepository.findAll();
	}
	
	@Admin
	@PostJson("/premarkets/{id}")
	public ResponseEntity<?> createMarket(@Valid @RequestBody PreMarketChange preMarketChange) {
		return preMarketManager.change(preMarketChange, new PreMarketChangeCallback() {

			@Override
			public ResponseEntity<?> success(Market market) {
				return ResponseEntity.created(URI.create("/")).body(market);
			}

			@Override
			public ResponseEntity<?> disapproved(PreMarket preMarket) {
				return ResponseEntity.ok().build();
			}
			
		});
	}
}
