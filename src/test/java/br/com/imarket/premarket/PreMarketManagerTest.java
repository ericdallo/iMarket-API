package br.com.imarket.premarket;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.imarket.market.Market;
import br.com.imarket.market.MarketCreateService;

@RunWith(MockitoJUnitRunner.class)
public class PreMarketManagerTest {
	
	private PreMarketManager subject;
	
	@Mock
	private PreMarketDTOToPreMarketConverter converter;
	@Mock
	private PreMarketRepository repository;
	@Mock
	private MarketCreateService marketCreateService;
	@Mock
	private PreMarketCreateCallback createCallback;
	@Mock
	private PreMarketChangeCallback changeCallback;
	@Mock
	private PreMarket preMarketToChange;
	@Mock
	private Market market;
	
	@Before
	public void setup() {
		subject = new PreMarketManager(converter, repository, marketCreateService);
	}

	@Test
	public void shouldCreatePreMarket() {
		PreMarketDTO dto = new PreMarketDTO();
		PreMarket createdPreMarket = new PreMarket();
		
		when(repository.findByCnpj(anyString())).thenReturn(Optional.empty());
		when(converter.convert(dto)).thenReturn(createdPreMarket);
		
		subject.create(dto, createCallback);
		
		verify(createCallback, never()).waitingApproval();
		verify(createCallback, never()).alreadyExists();
		verify(repository).save(createdPreMarket);
	}
	
	@Test
	public void shouldNotCreatePreMarketWhenAlreadyExists() {
		PreMarketDTO dto = new PreMarketDTO();
		PreMarket preMarket = new PreMarket();
		preMarket.approves();
		
		when(repository.findByCnpj(anyString())).thenReturn(Optional.of(preMarket));
		
		subject.create(dto, createCallback);
		
		verify(createCallback, never()).waitingApproval();
		verify(createCallback).alreadyExists();
		verify(repository, never()).save(preMarket);
	}
	
	@Test
	public void shouldNotCreatePreMarketWhenIsWaitingApproval() {
		PreMarketDTO dto = new PreMarketDTO();
		PreMarket preMarket = new PreMarket();
		
		when(repository.findByCnpj(anyString())).thenReturn(Optional.of(preMarket));
		
		subject.create(dto, createCallback);
		
		verify(createCallback).waitingApproval();
		verify(createCallback, never()).alreadyExists();
		verify(repository, never()).save(preMarket);
	}
	
	@Test
	public void shouldApprovePreMarketAndCreateMarket() {
		PreMarketChange preMarketChange = new PreMarketChange();
		preMarketChange.setId(123L);
		preMarketChange.setApproved(true);
		
		when(repository.findById(preMarketChange.getId())).thenReturn(Optional.of(preMarketToChange));
		when(marketCreateService.create(preMarketToChange)).thenReturn(market);
		
		subject.change(preMarketChange, changeCallback);
		
		verify(preMarketToChange).approves();
		verify(repository).save(preMarketToChange);
		verify(changeCallback).success(market);
		verify(changeCallback, never()).disapproved(preMarketToChange);
	}
	
	@Test
	public void shouldDisapprovePreMarket() {
		PreMarketChange preMarketChange = new PreMarketChange();
		preMarketChange.setId(123L);
		preMarketChange.setApproved(false);
		preMarketChange.setDisapprovedText("AnyReason");
		
		when(repository.findById(preMarketChange.getId())).thenReturn(Optional.of(preMarketToChange));
		
		subject.change(preMarketChange, changeCallback);
		
		verify(preMarketToChange).disapproves(preMarketChange.getDisapprovedText());
		verify(repository).save(preMarketToChange);
		verify(changeCallback, never()).success(market);
		verify(changeCallback).disapproved(preMarketToChange);
	}
	
	@Test(expected = PreMarketNotFoundException.class)
	public void shouldThrowsWhenPreMarketNotFound() {
		PreMarketChange preMarketChange = new PreMarketChange();
		
		when(repository.findById(preMarketChange.getId())).thenReturn(Optional.empty());
		
		subject.change(preMarketChange, changeCallback);
	}

}
