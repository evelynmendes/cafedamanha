package br.com.wl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class WebController {

	@GetMapping
	public String index() {
		return "home";
	}

	@GetMapping("/colaboradores")
	public String colaboradores() {
		return "colaboradores";
	}

	@GetMapping("/colaboradores/{colaboradorId}/atualizar")
	public String colaborador(@PathVariable("colaboradorId") Integer colaboradorId,
							  Model model) {

		model.addAttribute("colaboradorId", colaboradorId);
		return "colaborador";
	}

	@GetMapping("/colaboradores/{colaboradorId}")
	public String produtos(@PathVariable("colaboradorId") Integer colaboradorId,
						   Model model) {

		model.addAttribute("colaboradorId", colaboradorId);
		return "produtos";
	}

	@GetMapping("/colaboradores/{colaboradorId}/produtos/{produtoId}/atualizar")
	public String produto(@PathVariable("colaboradorId") Integer colaboradorId,
						  @PathVariable("produtoId") Integer produtoId,
						  Model model) {

		model.addAttribute("colaboradorId", colaboradorId);
		model.addAttribute("produtoId", produtoId);
		return "produto";
	}

}