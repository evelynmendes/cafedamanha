package br.com.wl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {
	
	@GetMapping
	public String index() {
		return "index";
	}
	
	@GetMapping("/colaborador")
	public String colaborador() {
		return "/page/colaborador";
	}
	
	@GetMapping("/produto")
	public String produto() {
		return "/page/produto";
	}
}
