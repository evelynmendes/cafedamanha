package br.com.wl.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.aspectj.apache.bcel.classfile.ClassVisitor;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import org.jboss.logging.Logger;
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

import br.com.wl.dto.ColaboradorDTO;
import br.com.wl.model.Colaborador;
import br.com.wl.repository.ColaboradorRepository;
import br.com.wl.service.ColaboradorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value = "/api/colaboradores", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class ColaboradorController {

	private final ColaboradorService colaboradorService;

	@GetMapping
	public ResponseEntity<List<Colaborador>> consultar() {
		List<Colaborador> colaborador = colaboradorService.consultar();
		return ResponseEntity.ok().body(colaborador);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Colaborador> consultarPorId(@PathVariable Integer id) {
		Colaborador colaborador = colaboradorService.consultarPorId(id);
		return ResponseEntity.ok().body(colaborador);
	}

	@PostMapping
	public ResponseEntity<Colaborador> inserir(@Valid @RequestBody ColaboradorDTO colaboradorDTO) {
		Colaborador colaborador = colaboradorService.inserir(colaboradorDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(colaborador.getId())
				.toUri();
		return ResponseEntity.created(uri).body(colaborador);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Colaborador> atualizar(@PathParam("id") Integer id,
			@RequestBody ColaboradorDTO colaboradorDTO) {

		Colaborador colaborador = colaboradorService.atualizar(id, colaboradorDTO);
		return ResponseEntity.ok().body(colaborador);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
		colaboradorService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
