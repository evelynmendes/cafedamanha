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
@RequestMapping(value = "/colaboradores", produces = "application/json")
@RestController
@RequiredArgsConstructor
public class ColaboradorController {

	@Autowired
	private final ColaboradorService colaboradorService;

	Logger logger = Logger.getLogger(ColaboradorRepository.class);

	@GetMapping
	public ResponseEntity<List<ColaboradorDTO>> consultar() {
		List<Colaborador> colaborador = colaboradorService.consultar();
		List<ColaboradorDTO> colaboradorDto = colaborador.stream().map(x -> new ColaboradorDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(colaboradorDto);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Colaborador>> consultarPorId(@PathVariable Integer id) {
		Optional<Colaborador> colaborador = colaboradorService.consultarPorId(id);
		return ResponseEntity.ok().body(colaborador);
	}

	@GetMapping(value = "/{cpf}")
	public ResponseEntity<Optional<Colaborador>> consultarPorCpf(@PathVariable String cpf) {
		Optional<Colaborador> colaborador = colaboradorService.consultarPorCpf(cpf);
		return ResponseEntity.ok().body(colaborador);
	}

	@PostMapping
	public ResponseEntity<Colaborador> inserir(@Valid @RequestBody ColaboradorDTO colaboradorDto,
			String cpf) {
		Optional<Colaborador> busca = colaboradorService.consultarPorCpf(cpf);

		if (busca != null) {
			throw new RuntimeException("Colaborador já cadastrado");
		}

		Colaborador colaborador = colaboradorService.inserir(colaboradorDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(colaborador.getId())
				.toUri();
		return ResponseEntity.created(uri).body(colaborador);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Colaborador> atualizar(@PathParam("id") Integer id,
			@RequestBody ColaboradorDTO colaboradorDto) {

		return colaboradorService.consultarPorId(id).map(colaborador -> {
			colaborador.setCpf(colaboradorDto.getCpf());
			colaborador.setNome(colaboradorDto.getNome());
			Colaborador atualizado = colaboradorService.atualizar(colaborador);
			return ResponseEntity.ok().body(atualizado);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ColaboradorDTO> deletar(@PathParam("id") Integer id) {
		return ResponseEntity.ok().build();
	}
}
