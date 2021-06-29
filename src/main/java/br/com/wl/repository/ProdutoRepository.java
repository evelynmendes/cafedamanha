package br.com.wl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wl.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	
	@Query(value = "SELECT * FROM PRODUTO ", nativeQuery = true)
	List<Produto> consultar();
	
	@Query(value = "SELECT * FROM PRODUTO p WHERE p.id = :id ", nativeQuery = true)
	Optional<Produto> consultarPorId(@Param("id") Integer id);

	@Query(value = "SELECT * FROM PRODUTO p WHERE p.nome_item = :nomeItem ", nativeQuery = true)
	Optional<Produto> consultarPorItem(@Param("nomeItem") String nomeItem);

	@Modifying
	@Query(value = "UPDATE PRODUTO p SET p.nome_item = :nomeItem WHERE p.id = :id", nativeQuery = true)
    void atualizar(@Param("id") Integer id, @Param("nomeItem") String nomeItem);

	@Modifying
	@Query(value = "DELETE FROM PRODUTO p WHERE p.id = :id", nativeQuery = true)
    void deletar(@Param("id") Integer id);

}
