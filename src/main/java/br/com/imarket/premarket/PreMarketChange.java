package br.com.imarket.premarket;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PreMarketChange {

	private Long id;
	private boolean approved;
	private String disapprovedText;
	
	@JsonCreator
	public static PreMarketChange create(String jsonString) {

	    PreMarketChange preMarketChange = null;
	    
		ObjectMapper mapper = new ObjectMapper();
		try {
			preMarketChange = mapper .readValue(jsonString, PreMarketChange.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    return preMarketChange;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public String getDisapprovedText() {
		return disapprovedText;
	}
	public void setDisapprovedText(String disapprovedText) {
		this.disapprovedText = disapprovedText;
	}
	
}
