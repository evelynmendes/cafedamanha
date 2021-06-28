package br.com.wl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wl.model.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

	@Query(value = "SELECT * FROM Colaborador", nativeQuery = true)
	List<Colaborador> consultar();

	@Query(value = "SELECT * FROM Colaborador c WHERE c.id = :id", nativeQuery = true)
	Optional<Colaborador> consultarPorId(Integer id);

	@Query(value = "SELECT * FROM Colaborador c WHERE c.cpf = :cpf", nativeQuery = true)
	Optional<Colaborador> consultarPorId(String cpf);

	@Query(value = "INSERT INTO Colaborador c ('c.cpf','c.nome','c.lista') VALUES ('cpf','nome','lista')", nativeQuery = true)
	Colaborador inserir(Colaborador colaborador);

	@Query(value = "UPDATE Colaborador c SET c.cpf = cpf, c.nome = nome, c.lista = lista WHERE c.id = id", nativeQuery = true)
	Colaborador atualizar(Colaborador colaborador);

	@Query(value = "DELETE FROM Colaborador c WHERE c.id = :id", nativeQuery = true)
	Colaborador deletar(Integer id);

}
