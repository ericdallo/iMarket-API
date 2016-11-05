package br.com.imarket.market.picture;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = MarketPicture.class, idClass = Long.class)
public interface MarketPictureRepository {

	MarketPicture save(MarketPicture marketPicture);

}
