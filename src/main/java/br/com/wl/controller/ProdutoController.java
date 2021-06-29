package br.com.wl.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value ="/api/produto", produces = "application/json")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	 
	@GetMapping 
	public ResponseEntity<List<Produto>> consultar(){
		List<Produto> produto = produtoService.consultar();
		return ResponseEntity.ok().body(produto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Produto>> consultarPorId(@PathVariable Integer id) {
		Optional<Produto> produto = produtoService.consultarPorId(id);
		return ResponseEntity.ok().body(produto); 
	}	
	
	@GetMapping(value = "/{nomeItem}")
	public ResponseEntity<Optional<Produto>> consultarPorItem(@PathVariable String nomeItem) {
		Optional<Produto> produto = produtoService.consultarPorItem(nomeItem);
		return ResponseEntity.ok().body(produto); 
	}	
	
	@PostMapping
	public ResponseEntity<Produto> inserir(@RequestBody ProdutoDTO produtoDto){
		produtoService.consultarPorItem(produtoDto.getNomeItem())
				.map(item -> item != null)
				.filter(Boolean::booleanValue)
				.orElseThrow(() -> new RuntimeException("Item já cadastrado por outro colaborador"));

		Produto produto = produtoService.inserir(produtoDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(produto);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody ProdutoDTO produtoDto) {
		
		return produtoService.consultarPorId(id)
		           .map(produto -> {
		        	   produto.setNomeItem(produtoDto.getNomeItem());
		        	   Produto atualizado = produtoService.atualizar(produto);
		               return ResponseEntity.ok().body(atualizado);
		           }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Produto> deletar(@PathVariable Integer id){
		return ResponseEntity.ok().build();		
	}
}
