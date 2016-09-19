package br.com.imarket;

import org.springframework.composed.web.Get;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

	@Get("/")
	public String index() {
		return "home/index";
	}
}
