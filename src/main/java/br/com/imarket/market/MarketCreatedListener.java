package br.com.imarket.market;

import static com.google.cloud.pubsub.Message.of;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.imarket.cloud.Topics;

@Component
class MarketCreatedListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MarketCreatedListener.class);
	
	@Autowired
	private Topics topics;
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Async
	@EventListener
	public void sendEmail(MarketCreatedEvent event) {
		Market market = event.getMarket();
		try {
			String marketJson = jacksonObjectMapper.writeValueAsString(market);
			
			topics.marketCreated()
				  .publishAsync(of(marketJson));
		} catch (JsonParseException je) {
			LOGGER.error("Could not deserialize market with id {}", market.getId());
		} catch (Exception e) {
			LOGGER.error("Could not publish event for {} with id {}", MarketCreatedEvent.class.getSimpleName(), market.getId());
		}
	}
}
