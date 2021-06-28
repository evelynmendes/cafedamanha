package br.com.wl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wl.dto.ColaboradorDTO;
import br.com.wl.model.Colaborador;
import br.com.wl.repository.ColaboradorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

	@Autowired
	private final ColaboradorRepository colaboradorRepository;

	public List<Colaborador> consultar() {
		List<Colaborador> colaborador = colaboradorRepository.consultar();
		return colaborador;
	}

	public Optional<Colaborador> consultarPorId(Integer id) {
		final Optional<Colaborador> colaborador = Optional
				.of(colaboradorRepository.consultarPorId(id).orElseThrow(() -> new RuntimeException("Not Found")));
		return colaborador;
	}

	public Optional<Colaborador> consultarPorCpf(String cpf) {
		final Optional<Colaborador> colaborador = Optional
				.of(colaboradorRepository.consultarPorId(cpf).orElseThrow(() -> new RuntimeException("Not Found")));
		return colaborador;
	}

	public Colaborador inserir(ColaboradorDTO colaboradorDto) {
		final Colaborador colaborador = new Colaborador();
		colaborador.setCpf(colaboradorDto.getCpf());
		colaborador.setNome(colaboradorDto.getNome());
		return colaboradorRepository.inserir(colaborador);
	}

	public Colaborador atualizar(Colaborador colaborador) {
		return colaboradorRepository.atualizar(colaborador);
	}

	public Colaborador deletar(Integer id) {
		return colaboradorRepository.deletar(id);
	}

}
