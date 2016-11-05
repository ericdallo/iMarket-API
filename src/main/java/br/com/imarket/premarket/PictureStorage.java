package br.com.imarket.premarket;

import static java.lang.String.format;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.imarket.cloud.StorageService;
import br.com.imarket.market.picture.MarketPicture;
import br.com.imarket.market.picture.MarketPictureRepository;

@Service
public class PictureStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(PictureStorage.class);
	
	@Value("${env}")
	private String env;
	@Value("${cloud.bucket.images}")
	private String imagesBucket;
	
	@Autowired
	private StorageService storageService;
	@Autowired
	private MarketPictureRepository marketPictureRepository;

	public MarketPicture store(MultipartFile multipartFile) {
		MarketPicture savedMarketPicture = marketPictureRepository.save(new MarketPicture(multipartFile.getOriginalFilename()));
		
		String path = format("%s/market/pictures/%s/", env, savedMarketPicture.getId());
		try {
			storageService.upload(imagesBucket, path, multipartFile);
		} catch (IOException e) {
			LOGGER.error("Cannot upload picture with pictureId {} ", savedMarketPicture.getId(), e);
		}
		
		marketPictureRepository.save(savedMarketPicture);
		return savedMarketPicture;
	}

}
