package com.ifood.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/about")
public class About {

    public About() {
    	System.out.println();
    }

}
