package br.com.wl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wl.dto.ListaCafeDTO;
import br.com.wl.model.ListaCafe;

@Repository
public interface ListaCafeRepository extends JpaRepository<ListaCafe, Integer> {

	
	@Query(value = "SELECT * FROM LISTA_CAFE ", nativeQuery = true)
	List<ListaCafe> consultar();
	
	@Query(value = "SELECT * FROM LISTA_CAFE l WHERE l.id = id ", nativeQuery = true)
	Optional<ListaCafe> consultarPorId(Integer id);

	@Query(value = "SELECT * FROM LISTA_CAFE l WHERE l.nome_item = nomeItem ", nativeQuery = true)
	Optional<ListaCafe> consultarPorItem(String nomeItem);
	
	@Query(value = "INSERT INTO LISTA_CAFE ('nome_item',lista) VALUES ('nomeItem','lista')", nativeQuery = true)
	ListaCafe inserir(ListaCafe listaDoCafe);

	@Query(value = "UPDATE LISTA_CAFE l SET l.nome_item = nomeItem, l.lista = lista WHERE l.id = id", nativeQuery = true)
	ListaCafe atualizar(ListaCafe listaDoCafe);

	@Query(value = "DELETE FROM LISTA_CAFE l WHERE c.id :=id", nativeQuery = true)
	ListaCafe deletar(Integer id);

}
