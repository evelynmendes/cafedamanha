package br.com.wl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.wl.dto.ProdutoDTO;
import br.com.wl.model.Colaborador;
import br.com.wl.model.Produto;
import br.com.wl.repository.ColaboradorRepository;
import br.com.wl.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final ColaboradorRepository colaboradorRepository;

	public List<Produto> consultar() {
		List<Produto> produto = produtoRepository.consultar();
		return produto;
	}

	public Optional<Produto> consultarPorId(Integer id) {
		final Optional<Produto> colaborador = Optional
				.of(produtoRepository.consultarPorId(id).orElseThrow(() -> new RuntimeException("Not Found")));
		return colaborador;
	}

	public Optional<Produto> consultarPorItem(String nomeItem) {
		final Optional<Produto> colaborador = Optional
				.of(produtoRepository.consultarPorItem(nomeItem).orElseThrow(() -> new RuntimeException("Not Found")));
		return colaborador;
	}

	public Produto inserir(final ProdutoDTO produtoDTO) {
		Colaborador colaborador = colaboradorRepository.consultarPorId(produtoDTO.getIdColaborador())
				.orElseThrow(() -> new RuntimeException("Colaborador Not Found"));

		produtoRepository.consultarPorItem(produtoDTO.getNomeItem().toUpperCase()).ifPresent(p -> {
			throw new RuntimeException("Already saved");
		});

		colaborador.setId(colaborador.getId());
		colaborador.setNome(colaborador.getNome());
		colaborador.setCpf(colaborador.getCpf());
		Produto produto = new Produto();
		produto.setNomeItem(produtoDTO.getNomeItem().toUpperCase());
		colaborador.getLista().add(produto);
		colaboradorRepository.save(colaborador);
		colaboradorRepository.flush();
		return colaborador.getLista().stream().filter(item -> item.equals(produto)).findFirst().get();
	}

	public Produto atualizar(Produto produto) {
		produtoRepository.save(produto);
		return produto;
	}

	public void deletar(Integer id) {
		produtoRepository.deleteById(id);
	}

}
