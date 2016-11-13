package br.com.imarket.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.PubSub;
import com.google.cloud.pubsub.Topic;

@Component
public class Topics {
	
	@Autowired
	private Environment env;
	@Autowired
	private PubSub pubSub;

	public Topic marketCreated() {
		return pubSub.getTopic(env.getProperty("cloud.pubsub.market.created"));
	}
}
