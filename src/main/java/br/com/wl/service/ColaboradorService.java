package br.com.wl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.wl.dto.ColaboradorDTO;
import br.com.wl.model.Colaborador;
import br.com.wl.repository.ColaboradorRepository;
import br.com.wl.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

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

	public Colaborador inserir(ColaboradorDTO colaboradorDto) {
		Optional<Colaborador> busca = colaboradorRepository.consultarPorCPF(colaboradorDto.getCpf());

		busca.ifPresent(c -> {
			throw new RuntimeException("Colaborador já cadastrado");
		});

		final Colaborador colaborador = new Colaborador();
		colaborador.setCpf(colaboradorDto.getCpf());
		colaborador.setNome(colaboradorDto.getNome());
		return colaboradorRepository.save(colaborador);
	}

	public Colaborador atualizar(Colaborador colaborador) {
		return colaboradorRepository.save(colaborador);
	}

	public void deletar(Integer id) {
		colaboradorRepository.deleteById(id);
	}

}
