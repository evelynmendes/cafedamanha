package br.com.wl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.wl.dto.ColaboradorDTO;
import br.com.wl.model.Colaborador;
import br.com.wl.repository.ColaboradorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

	private final ColaboradorRepository repository;

	public List<Colaborador> consultar() {
		List<Colaborador> colaborador = repository.listarColaboradores();
		return colaborador;
	}

	public Colaborador consultarPorId(Integer id) {
		final Colaborador colaborador = Optional
				.ofNullable(repository.buscarColaboradorPorId(id))
				.orElseThrow(() -> new RuntimeException("Not Found"));
		return colaborador;
	}

	public Colaborador inserir(ColaboradorDTO colaboradorDTO) {
		Colaborador busca = repository.buscarColaboradorPorCPF(colaboradorDTO.getCpf());

		if (busca != null) {
			throw new RuntimeException("Colaborador já cadastrado");
		};

		final Colaborador colaborador = new Colaborador();
		colaborador.setCpf(colaboradorDTO.getCpf());
		colaborador.setNome(colaboradorDTO.getNome().toUpperCase());
		return repository.inserirColaborador(colaborador);
	}

	public Colaborador atualizar(Integer id, ColaboradorDTO colaboradorDTO) {
		final Colaborador colaborador = Optional.ofNullable(repository.buscarColaboradorPorId(id))
				.orElseThrow(() -> new RuntimeException("Not Found"));

		colaborador.setNome(colaboradorDTO.getNome());
		colaborador.setCpf(colaboradorDTO.getCpf());
		return repository.atualizarColaborador(colaborador);
	}

	public void deletar(Integer id) {
		repository.removerColaborador(id);
	}

}