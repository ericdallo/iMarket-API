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
class PubSubMarketCreatedListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PubSubMarketCreatedListener.class);
	
	@Autowired
	private Topics topics;
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Async
	@EventListener
	public void sendEmail(MarketCreatedEvent event) {
		try {
			String marketJson = jacksonObjectMapper.writeValueAsString(event);
			LOGGER.debug("serialized market: {}", marketJson);
			
			topics.marketCreated()
				  .publishAsync(of(marketJson));
		} catch (JsonParseException je) {
			LOGGER.error("Could not deserialize market with id {}", event.getMarket().getId());
		} catch (Exception e) {
			LOGGER.error("Could not publish event for {} with id {}", MarketCreatedEvent.class.getSimpleName(), event.getMarket().getId());
		}
	}
}
