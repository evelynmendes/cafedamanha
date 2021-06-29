package br.com.wl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wl.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

	@Query(value = "SELECT * FROM Colaborador", nativeQuery = true)
	List<Colaborador> consultar();

	@Query(value = "SELECT * FROM Colaborador c WHERE c.id = :id", nativeQuery = true)
	Optional<Colaborador> consultarPorId(@Param("id") Integer id);

	@Query(value = "SELECT * FROM Colaborador c WHERE c.cpf = :cpf", nativeQuery = true)
	Optional<Colaborador> consultarPorCPF(@Param("cpf") String cpf);

	@Modifying
	@Query(value = "INSERT INTO Colaborador c ('c.cpf','c.nome') VALUES (:nome, :cpf)", nativeQuery = true)
	Colaborador inserir(@Param("nome") String nome, @Param("cpf") String cpf);

	@Query(value = "UPDATE Colaborador c SET c.cpf = cpf, c.nome = nome, c.lista = lista WHERE c.id = id", nativeQuery = true)
	Colaborador atualizar(Colaborador colaborador);

	@Query(value = "DELETE FROM Colaborador c WHERE c.id = :id", nativeQuery = true)
	Colaborador deletar(Integer id);

}