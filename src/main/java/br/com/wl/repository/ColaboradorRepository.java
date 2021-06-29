package br.com.wl.repository;

import java.util.List;
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
        Object[] result = (Object[]) entityManager
                .createNativeQuery("SELECT * FROM Colaborador c WHERE c.id = :id")
                .setParameter("id", id)
                .getSingleResult();

        Colaborador colaborador = new Colaborador();
        colaborador.setId((Integer) result[0]);
        colaborador.setNome((String) result[1]);
        colaborador.setCpf((String) result[2]);
        colaborador.setProdutos(produtoRepository.listarProdutosPorColaborador(colaborador.getId()));
        return colaborador;
    }

    public Colaborador buscarColaboradorPorCPF(String cpf) {
        try {
            Object[] result = (Object[]) entityManager
                    .createNativeQuery("SELECT * FROM Colaborador c WHERE c.cpf = :cpf")
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            Colaborador colaborador = new Colaborador();
            colaborador.setId((Integer) result[0]);
            colaborador.setNome((String) result[1]);
            colaborador.setCpf((String) result[2]);
            colaborador.setProdutos(produtoRepository.listarProdutosPorColaborador(colaborador.getId()));
            return colaborador;
        }catch (Exception ex) {
            return null;
        }
    }

    public List<Colaborador> listarColaboradores() {
        List<Object[]> result = entityManager
                .createNativeQuery("SELECT * FROM Colaborador c")
                .getResultList();

        List<Colaborador> colaboradores = result.stream()
                .map(item -> {
                    Colaborador colaborador = new Colaborador();
                    colaborador.setId((Integer) item[0]);
                    colaborador.setNome((String) item[1]);
                    colaborador.setCpf((String) item[2]);
                    colaborador.setProdutos(produtoRepository.listarProdutosPorColaborador(colaborador.getId()));
                    return colaborador;
                })
                .collect(Collectors.toList());
        return colaboradores;
    }

    @Transactional
    public Colaborador inserirColaborador(Colaborador colaborador) {
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
            return result;
        }catch (Exception ex) {
            log.error("Error", ex);
            return 0;
        }
    }
}

