package br.com.imarket.premarket;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.composed.web.Post;
import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.composed.web.rest.json.PostJson;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.imarket.exception.FileTooLargeException;
import br.com.imarket.exception.MarketAlreadyExistsException;
import br.com.imarket.exception.MarketWaitingApprovalException;
import br.com.imarket.market.Market;
import br.com.imarket.market.MarketService;

@RestController
public class PreMarketController {

	@Value("${picture.upload.max.size}")
	private Long maxSize;
	
	@Autowired
	private PreMarketRepository preMarketRepository;
	@Autowired
	private MarketService marketService;
	@Autowired
	private PictureStorage pictureStorage;
	@Autowired
	private PreMarketManager preMarketManager;

	@PostJson("/premarkets")
	public void create(@Valid @RequestBody PreMarketDTO dto) {
		preMarketManager.create(dto, new PreMarketCallback() {

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
		if (file.getSize() > maxSize) {
			throw new FileTooLargeException();
		}
		return pictureStorage.store(file).getId();
	}
	
	@GetJson("/api/premarkets")
	public List<PreMarket> list() {
		return preMarketRepository.findAll();
	}
	
	@PostJson("/api/premarkets/{id}")
	public Market createMarket(@Valid @RequestBody PreMarketChange preMarketChange) {
		PreMarket preMarket = preMarketRepository.findById(preMarketChange.getId()).orElseThrow(PreMarketNotFoundException::new);
		
		if (preMarketChange.isApproved()) {
			Market market = marketService.create(preMarket);
			return market;
		}
		
		preMarket.disapproves(preMarketChange.getDisapprovedText());
		preMarketRepository.save(preMarket);
		
		return null;
	}
	
}
