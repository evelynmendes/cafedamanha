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

	private final ColaboradorRepository colaboradorRepository;
	private final ProdutoRepository repository;

	public List<Produto> consultar() {
		List<Produto> listaCafe = repository.listarProdutos();
		return listaCafe;
	}

	public Produto consultarPorId(Integer id) {
		final Produto produto = Optional.ofNullable(repository.buscarProdutoPorId(id))
				.orElseThrow(() -> new RuntimeException("Not Found"));
		return produto;
	}
	
	public Produto consultarPorItem(String nomeItem) {
		final Produto produto = Optional.ofNullable(repository.buscarProdutoPorNome(nomeItem))
				.orElseThrow(() -> new RuntimeException("Not Found"));
		return produto;
	}

	public Produto inserir(final ProdutoDTO produtoDTO) {
		Colaborador colaborador = Optional
				.ofNullable(colaboradorRepository.buscarColaboradorPorId(produtoDTO.getIdColaborador()))
				.orElseThrow(() -> new RuntimeException("Colaborador Not Found"));

		Optional.ofNullable(repository.buscarProdutoPorNome(produtoDTO.getNomeItem().toUpperCase()))
				.ifPresent(p -> {
					throw new RuntimeException("Already saved");
				});

		Produto produto = new Produto();
		produto.setNomeItem(produtoDTO.getNomeItem().toUpperCase());
		produto = repository.inserirProduto(produto, produtoDTO.getIdColaborador());
		return produto;
	}

	public Produto atualizar(Integer id, ProdutoDTO produtoDTO) {
		Produto produto = Optional.ofNullable(repository.buscarProdutoPorId(id))
				.orElseThrow(() -> new RuntimeException("Not Found"));

		produto.setNomeItem(produtoDTO.getNomeItem());
		return repository.atualizarProduto(produto);
	}

	public void deletar(Integer id) {
		repository.removerProduto(id);
	}

}