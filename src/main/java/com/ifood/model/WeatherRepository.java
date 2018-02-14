package com.ifood.model;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ifood.response.WeatherResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Service
@CacheConfig(cacheNames = "weather")
public class WeatherRepository {
	HashMap<String, WeatherResponse> cities = new HashMap<String, WeatherResponse>();

	@CacheEvict(allEntries = true)
	public void clearCache() {
		System.out.println("cleaning cache");
	}

	@HystrixCommand(fallbackMethod = "reliable", ignoreExceptions = {
			HttpClientErrorException.class }, commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
	@Cacheable()
	public String getByCityAndCountry(String city, String country, String url) {
		String response = "";
		WeatherResponse weather = null;
		try {
			weather = new RestTemplate().getForObject(url + "&q=" + city + "," + "Br", WeatherResponse.class);
			cities.put(city, weather);
			response = "Temperatura em " + city + " " + weather.getMain().getTemp();

		} catch (HttpClientErrorException e) {
			response = "Cidade não encontrada";
		}

		return response;
	}

	@SuppressWarnings("unused")
	private String reliable(String city, String country, String url) {
		String response = null;
		WeatherResponse weather = null;
		if (cities.containsKey(city)) {
			weather = cities.get(city);
			response = "Temperatura em " + city + " " + weather.getMain().getTemp();
			response += "<br /> Serviço offline. <br /> Temperatura verificada a +- "
					+ timeDistance(weather.getCreated(), LocalDateTime.now());
			return response;
		} else {
			while (city.length() > 0) {
				if (cities.containsKey(city)) {
					weather = cities.get(city);
					response = "Você quis dizer <a href=http://localhost:8080/weather?city=" + city + ">" + city
							+ "</a>?";
					return response;
				}
				city = city.substring(0, city.length() - 1);
			}
		}

		return response;
	}

	// Public for testing purpose
	public String timeDistance(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		HashMap<String, Long> dateAndTime = new HashMap<String, Long>();
		String result = "";
		LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

		// Eu sei que não preciso declarar estas varíáveis, deixei aqui apenas para
		// ficar mais "human friendly"
		long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears(years);
		long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
		tempDateTime = tempDateTime.plusMonths(months);
		long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
		tempDateTime = tempDateTime.plusDays(days);
		long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours(hours);
		long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);
		long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

		dateAndTime.put("years", years);
		dateAndTime.put("months", months);
		dateAndTime.put("days", days);
		dateAndTime.put("hours", hours);
		dateAndTime.put("minutes", minutes);
		dateAndTime.put("seconds", seconds);

		if (howManyOthers(dateAndTime) > 0) {
			if (dateAndTime.get("years") > 0) {
				result += dateAndTime.get("years") + (dateAndTime.get("years") == 1 ? " ano" : " anos");
				dateAndTime.put("years", null);
				result += howManyOthers(dateAndTime) > 0 ? howManyOthers(dateAndTime) == 1 ? " e " : ", " : "";
			}

			if (dateAndTime.get("months") > 0) {
				result += dateAndTime.get("months") + (dateAndTime.get("months") == 1 ? " mês" : " meses");
				dateAndTime.put("months", null);
				result += howManyOthers(dateAndTime) > 0 ? howManyOthers(dateAndTime) == 1 ? " e " : ", " : "";
			}

			if (dateAndTime.get("days") > 0) {
				result += dateAndTime.get("days") + (dateAndTime.get("days") == 1 ? " dia" : " dias");
				dateAndTime.put("days", null);
				result += howManyOthers(dateAndTime) > 0 ? howManyOthers(dateAndTime) == 1 ? " e " : ", " : "";
			}

			if (dateAndTime.get("hours") > 0) {
				result += dateAndTime.get("hours") + (dateAndTime.get("hours") == 1 ? " hora" : " horas");
				dateAndTime.put("hours", null);
				result += howManyOthers(dateAndTime) > 0 ? howManyOthers(dateAndTime) == 1 ? " e " : ", " : "";
			}

			if (dateAndTime.get("minutes") > 0) {
				result += dateAndTime.get("minutes") + (dateAndTime.get("minutes") == 1 ? " minuto" : " minutos");
				dateAndTime.put("minutes", null);
				result += howManyOthers(dateAndTime) > 0 ? howManyOthers(dateAndTime) == 1 ? " e " : ", " : "";
			}

			if (dateAndTime.get("seconds") > 0) {
				result += dateAndTime.get("seconds") + (dateAndTime.get("seconds") == 1 ? " segundo" : " segundos");
			}

			result += ".";
		}

		return result;
	}

	private int howManyOthers(HashMap<String, Long> dateAndTime) {
		int result = 0;

		if (dateAndTime.get("years") != null && dateAndTime.get("years") > 0)
			result++;
		if (dateAndTime.get("months") != null && dateAndTime.get("months") > 0)
			result++;
		if (dateAndTime.get("days") != null && dateAndTime.get("days") > 0)
			result++;
		if (dateAndTime.get("hours") != null && dateAndTime.get("hours") > 0)
			result++;
		if (dateAndTime.get("minutes") != null && dateAndTime.get("minutes") > 0)
			result++;
		if (dateAndTime.get("seconds") != null && dateAndTime.get("seconds") > 0)
			result++;

		return result;
	}

}
