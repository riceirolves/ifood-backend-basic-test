package com.ifood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ifood.model.WeatherRepository;
import com.ifood.response.WeatherResponse;

@RestController
@EnableCircuitBreaker
public class WeatherController {

	String url = "http://api.openweathermap.org/data/2.5/weather?APPID=f249c92ff5137f5d90b8cf1ae2fc7bf9&units=metric";

	@Autowired
	private WeatherRepository weatherRepository;

	@RequestMapping("/grandeCampinas")
	public String weather() {

		String response = "";
		String[] cities = { "campinas", "valinhos", "vinhedo", "paulinia" };

		for (String city : cities)
			response += weather(city, "Br") + "<br /> <br />";
		return response;
	}

	@RequestMapping("/weather")
	public String weather(@RequestParam(value = "city", defaultValue = "campinas") String city,
			@RequestParam(value = "country", defaultValue = "br") String country) {
		String response = "";
		city = city.toLowerCase();
		country = country.toLowerCase();
		long startTime = System.currentTimeMillis();
		response = weatherRepository.getByCityAndCountry(city, country, url);
		if (response != null) {
			if (response.equals("Cidade não encontrada")) {
				response = tryToFixGrammar(city, country);
				if (response == null) {
					response = "Cidade não encontrada";
				}
			}
			response += " <br />(Latência: " + ((System.currentTimeMillis() - startTime)) + "ms)";
		} else {
			response = "Serviço indisponível";
		}
		return response;
	}

	public String tryToFixGrammar(String city, String country) {

		city = city.substring(0, city.length() - 1);
		if (city.length() > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity = new HttpEntity<Object>(headers);
			RestTemplate restTemplate = new RestTemplate();
			try {
				if (restTemplate
						.exchange(url + "&q=" + city + "," + "Br", HttpMethod.GET, entity, WeatherResponse.class)
						.getStatusCodeValue() == 200) {
					return "Você quis dizer <a href=http://localhost:8080/weather?city=" + city + ">" + city + "</a>?";
				}
			} catch (HttpClientErrorException e) {
				return tryToFixGrammar(city, country);
			} catch (Exception e) {
			}
		}
		return null;
	}

}
