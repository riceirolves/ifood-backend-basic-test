package com.ifood.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

	MainResponse main = new MainResponse();
	LocalDateTime created = LocalDateTime.now();

	public WeatherResponse() {
	}

	public MainResponse getMain() {
		return main;
	}

	public void setMain(MainResponse main) {
		this.main = main;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}
