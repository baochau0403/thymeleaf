package vn.iotstar.thymeleaf.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String imdex() {
		return "admin/categories/list";
	}
}
