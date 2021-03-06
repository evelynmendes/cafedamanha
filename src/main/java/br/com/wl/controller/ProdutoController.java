package br.com.wl.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.wl.dto.ProdutoDTO;
import br.com.wl.model.Produto;
import br.com.wl.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value ="/api/produtos", produces = "application/json")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService produtoService;
	 
	@GetMapping 
	public ResponseEntity<List<Produto>> listar() {
		log.info("listar");
		List<Produto> listaCafe = produtoService.listar();
		return ResponseEntity.ok().body(listaCafe);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> consultarPorId(@PathVariable Integer id) {
		log.info("consultarPorId");
		Produto produto = produtoService.consultarPorId(id);
		return ResponseEntity.ok().body(produto);
	}	
	
	@PostMapping
	public ResponseEntity<Produto> inserir(@Valid @RequestBody ProdutoDTO produtoDTO) {
		log.info("inserir");
		Produto produto = produtoService.inserir(produtoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(produto);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @Valid @RequestBody ProdutoDTO produtoDTO) {
		log.info("atualizar");
		Produto produto = produtoService.atualizar(id, produtoDTO);
		return ResponseEntity.ok().body(produto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {
		log.info("deletar");
		produtoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}