package br.com.wl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wl.dto.ProdutoDTO;
import br.com.wl.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	
	@Query(value = "SELECT * FROM PRODUTO ", nativeQuery = true)
	List<Produto> consultar();
	
	@Query(value = "SELECT * FROM PRODUTO p WHERE p.id = id ", nativeQuery = true)
	Optional<Produto> consultarPorId(Integer id);

	@Query(value = "SELECT * FROM PRODUTO p WHERE p.nome_item = nomeItem ", nativeQuery = true)
	Optional<Produto> consultarPorItem(String nomeItem);
	
	@Query(value = "INSERT INTO PRODUTO ('nome_item',lista) VALUES ('nomeItem','lista')", nativeQuery = true)
	Produto inserir(Produto listaDoCafe);

	@Query(value = "UPDATE PRODUTO p SET p.nome_item = nomeItem, p.lista = lista WHERE p.id = id", nativeQuery = true)
	Produto atualizar(Produto listaDoCafe);

	@Query(value = "DELETE FROM PRODUTO p WHERE p.id :=id", nativeQuery = true)
	Produto deletar(Integer id);

}
