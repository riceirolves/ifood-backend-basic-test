package com.ifood.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {

	@RequestMapping("/about")
	public String about() {
		return "Hello World";
	}
}
