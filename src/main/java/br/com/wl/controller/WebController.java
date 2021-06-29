package br.com.wl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("/colaboradores")
	public String colaboradores() {
		return "/page/colaboradores";
	}

	@GetMapping("/colaboradores/{id}")
	public String produtos(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("colaboradorId",id);
		return "/page/produtos";
	}

	@GetMapping("/cadastrar")
	public String cadastrar() {
		return "/page/cadastrar";
	}
}