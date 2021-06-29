package br.com.wl.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.wl.model.Colaborador;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ColaboradorRepository {

    private final EntityManager entityManager;
    private final ProdutoRepository produtoRepository;

    public Colaborador buscarColaboradorPorId(Integer id) {
        try {
            log.info("Buscando colaborador por id {}", id);
            Object[] result = (Object[]) entityManager
                    .createNativeQuery("SELECT * FROM Colaborador c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            Colaborador colaborador = new Colaborador();
            colaborador.setId((Integer) result[0]);
            colaborador.setCpf((String) result[1]);
            colaborador.setNome((String) result[2]);
            colaborador.setProdutos(produtoRepository.listarProdutosPorColaborador(colaborador.getId()));
            return colaborador;
        }catch (Exception ex) {
            return null;
        }
    }

    public Colaborador buscarColaboradorPorCPF(String cpf) {
        try {
            log.info("Buscando colaborador por cpf {}", cpf);
            Object[] result = (Object[]) entityManager
                    .createNativeQuery("SELECT * FROM Colaborador c WHERE c.cpf = :cpf")
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            Colaborador colaborador = new Colaborador();
            colaborador.setId((Integer) result[0]);
            colaborador.setCpf((String) result[1]);
            colaborador.setNome((String) result[2]);
            colaborador.setProdutos(produtoRepository.listarProdutosPorColaborador(colaborador.getId()));
            return colaborador;
        }catch (Exception ex) {
            return null;
        }
    }

    public List<Colaborador> listarColaboradores() {
        try {
            log.info("Listando colaboradores");
            List<Object[]> result = entityManager
                    .createNativeQuery("SELECT * FROM Colaborador c")
                    .getResultList();

            List<Colaborador> colaboradores = result.stream()
                    .map(item -> {
                        Colaborador colaborador = new Colaborador();
                        colaborador.setId((Integer) item[0]);
                        colaborador.setCpf((String) item[1]);
                        colaborador.setNome((String) item[2]);
                        colaborador.setProdutos(produtoRepository.listarProdutosPorColaborador(colaborador.getId()));
                        return colaborador;
                    })
                    .collect(Collectors.toList());
            return colaboradores;
        }catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    @Transactional
    public Colaborador inserirColaborador(Colaborador colaborador) {
        log.info("Inserindo colaborador de nome {}", colaborador.getNome());
        colaborador.setId(getColaboradorMaxId() + 1);
        int result = entityManager
                .createNativeQuery("INSERT INTO Colaborador (id, nome, cpf) VALUES (?, ?, ?)")
                .setParameter(1, colaborador.getId())
                .setParameter(2, colaborador.getNome())
                .setParameter(3, colaborador.getCpf())
                .executeUpdate();

        return buscarColaboradorPorId(colaborador.getId());
    }

    @Transactional
    public Colaborador atualizarColaborador(Colaborador colaborador) {
        log.info("Atualizando colaborador de nome {}", colaborador.getNome());
        int result = entityManager
                .createNativeQuery("UPDATE Colaborador SET nome = ?, cpf = ? WHERE id = ?")
                .setParameter(1, colaborador.getNome())
                .setParameter(2, colaborador.getCpf())
                .setParameter(3, colaborador.getId())
                .executeUpdate();

        return buscarColaboradorPorId(colaborador.getId());
    }


    @Transactional
    public void removerColaborador(Integer id) {
        log.info("Removendo colaborador por id {}", id);
        entityManager
                .createNativeQuery("DELETE FROM Produto WHERE colaborador_id = ?")
                .setParameter(1, id)
                .executeUpdate();

        entityManager
                .createNativeQuery("DELETE FROM Colaborador WHERE id = ?")
                .setParameter(1, id)
                .executeUpdate();
    }

    private Integer getColaboradorMaxId() {
        try {
            Integer result = (Integer) entityManager
                    .createNativeQuery("SELECT MAX(id) FROM Colaborador c")
                    .getSingleResult();
            return Optional.ofNullable(result).orElse(0);
        }catch (Exception ex) {
            log.error("Error", ex);
            return 0;
        }
    }
}