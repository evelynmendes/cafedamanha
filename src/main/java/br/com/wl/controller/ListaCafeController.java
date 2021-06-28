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

import br.com.wl.dto.ListaCafeDTO;
import br.com.wl.model.ListaCafe;
import br.com.wl.service.ListaCafeService;

@RestController
@RequestMapping(value ="/cafe", produces = "application/json")
public class ListaCafeController {

	@Autowired
	private ListaCafeService listaCafeService;
	 
	@GetMapping 
	public ResponseEntity<List<ListaCafeDTO>> consultar(){
		List<ListaCafe> listaCafe = listaCafeService.consultar();
		List<ListaCafeDTO> listaCafeDto = listaCafe.stream().map(x -> new ListaCafeDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaCafeDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<ListaCafe>> consultarPorId(@PathVariable Integer id) {
		Optional<ListaCafe> listaCafe = listaCafeService.consultarPorId(id);
		return ResponseEntity.ok().body(listaCafe); 
	}	
	
	@GetMapping(value = "/{nomeItem}")
	public ResponseEntity<Optional<ListaCafe>> consultarPorId(@PathVariable String nomeItem) {
		Optional<ListaCafe> listaCafe = listaCafeService.consultarPorItem(nomeItem);
		return ResponseEntity.ok().body(listaCafe); 
	}	
	
	@PostMapping
	public ResponseEntity<ListaCafe> inserir(@RequestBody ListaCafeDTO listaCafeDto, String nomeItem ){
		Optional<ListaCafe> buscaItem = listaCafeService.consultarPorItem(nomeItem); 
		
		if(buscaItem != null) {
			throw new RuntimeException("Item já cadastrado por outro colaborador");
		}
		
		ListaCafe listaCafe = listaCafeService.inserir(listaCafeDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(listaCafe.getId())
				.toUri();
		return ResponseEntity.created(uri).body(listaCafe);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<ListaCafe> atualizar(@PathVariable Integer id, @RequestBody ListaCafeDTO listaCafeDto) {
		
		return listaCafeService.consultarPorId(id)
		           .map(listaCafe -> {
		        	   listaCafe.setNomeItem(listaCafeDto.getNomeItem());
		        	   listaCafe.setLista(listaCafeDto.getLista());
		        	   ListaCafe atualizado = listaCafeService.atualizar(listaCafe);
		               return ResponseEntity.ok().body(atualizado);
		           }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ListaCafe> deletar(@PathVariable Integer id){
		return ResponseEntity.ok().build();		
	}
}
