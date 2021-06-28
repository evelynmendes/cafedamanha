package br.com.wl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wl.dto.ListaCafeDTO;
import br.com.wl.model.ListaCafe;
import br.com.wl.repository.ListaCafeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ListaCafeService {

	@Autowired
	private ListaCafeRepository repository;

	public List<ListaCafe> consultar() {
		List<ListaCafe> listaCafe = repository.consultar();
		return listaCafe;
	}

	public Optional<ListaCafe> consultarPorId(Integer id) {
		final Optional<ListaCafe> colaborador = Optional
				.of(repository.consultarPorId(id).orElseThrow(() -> new RuntimeException("Not Found")));
		return colaborador;
	}
	
	public Optional<ListaCafe> consultarPorItem(String nomeItem) {
		final Optional<ListaCafe> colaborador = Optional
				.of(repository.consultarPorItem(nomeItem).orElseThrow(() -> new RuntimeException("Not Found")));
		return colaborador;
	}

	public ListaCafe inserir(final ListaCafeDTO listaCafeDto) {
		final ListaCafe listaCafe = new ListaCafe();
		listaCafe.setNomeItem(listaCafeDto.getNomeItem().toUpperCase());
		listaCafe.setLista(listaCafeDto.getLista());
		return repository.inserir(listaCafe);
	}

	public ListaCafe atualizar(ListaCafe listaDoCafe) {
		return repository.atualizar(listaDoCafe);
	}

	public ListaCafe deletar(Integer id) {
		return repository.deletar(id);
	}

}
