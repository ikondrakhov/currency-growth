package com.currency;

import com.currency.gif.Gif;
import com.currency.api.GifApi;
import com.currency.gif.GifData;
import com.currency.gif.Image;
import com.currency.services.CurrencyGrowthService;
import com.currency.api.CurrencyApi;
import com.currency.currency.Rates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
@RunWith(SpringRunner.class)
class CurrencyApplicationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CurrencyGrowthService currencyGrowthService;
	@MockBean
	private GifApi gifApi;
	@MockBean
	private CurrencyApi currencyApi;

	private Date today, yesterday;
	private Rates testRatesBig, testRatesSmall;
	private Gif testGif;

	@TestConfiguration
	static class CurrencyApplicationTestConfiguration {
		@Bean
		public CurrencyGrowthService currencyGrowthService() {
			return new CurrencyGrowthService();
		}
	}

	@BeforeEach
	public void setup() {
		this.today = new Date();

		Map<String, Float> bigRates = new HashMap<>();
		bigRates.put("EUR", 1.5f);
		this.testRatesBig = new Rates(today.getTime(), bigRates);

		Map<String, Float> smallRates = new HashMap<>();
		smallRates.put("EUR", 1.1f);
		this.testRatesSmall = new Rates(today.getTime(), smallRates);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(testRatesBig.getTimestamp() * 1000));
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		this.yesterday = calendar.getTime();

		Image image = new Image("URL");
		Map<String, Image> images = new HashMap<>();
		images.put("original", image);
		GifData gifData = new GifData(images);
		this.testGif = new Gif(gifData);

		Mockito.when(gifApi.getRichGif()).thenReturn(testGif);
		Mockito.when(gifApi.getBrokeGif()).thenReturn(testGif);
	}

	@Test
	public void getRichGif() throws Exception{
		Mockito.when(currencyApi.getLatestRates()).thenReturn(testRatesBig);
		Mockito.when(currencyApi.getRatesByDate(yesterday)).thenReturn(testRatesSmall);

		mockMvc.perform(MockMvcRequestBuilders.get("/currency-growth?currencyCode=EUR"));

		verify(gifApi, times(1)).getRichGif();
		verify(gifApi, times(0)).getBrokeGif();
	}

	@Test
	public void getBrokeGif() throws Exception{
		Mockito.when(currencyApi.getLatestRates()).thenReturn(testRatesSmall);
		Mockito.when(currencyApi.getRatesByDate(yesterday)).thenReturn(testRatesBig);

		mockMvc.perform(MockMvcRequestBuilders.get("/currency-growth?currencyCode=EUR"));

		verify(gifApi, times(0)).getRichGif();
		verify(gifApi, times(1)).getBrokeGif();
	}

	@Test
	public void currencyDoesNotExist() throws Exception{
		Mockito.when(currencyApi.getLatestRates()).thenReturn(testRatesSmall);
		Mockito.when(currencyApi.getRatesByDate(yesterday)).thenReturn(testRatesBig);

		mockMvc.perform(MockMvcRequestBuilders.get("/currency-growth?currencyCode=AAA"))
						.andExpect(view().name("error"));

		verify(gifApi, times(0)).getRichGif();
		verify(gifApi, times(0)).getBrokeGif();
	}
}
