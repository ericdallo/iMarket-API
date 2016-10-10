package br.com.imarket;

import org.springframework.composed.web.rest.json.GetJson;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


	@GetJson("/")
	public String index() {
		return "home";
	}
}
