package br.com.wl.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.wl.model.Produto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProdutoRepository {

    private final EntityManager entityManager;

    public List<Produto> listarProdutos() {
        try {
            log.info("Listando produtos");
            List<Object[]> result = entityManager
                    .createNativeQuery("SELECT * FROM Produto p")
                    .getResultList();

            List<Produto> produtos = result.stream()
                    .map(item -> {
                        Produto produto = new Produto();
                        produto.setId((Integer) item[0]);
                        produto.setNomeItem((String) item[1]);
                        return produto;
                    })
                    .collect(Collectors.toList());
            return produtos;
        }catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public Produto buscarProdutoPorId(Integer id) {
        try {
            log.info("Buscando produto por id {}", id);
            Object[] result = (Object[]) entityManager
                    .createNativeQuery("SELECT * FROM Produto p WHERE p.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            Produto produto = new Produto();
            produto.setId((Integer) result[0]);
            produto.setNomeItem((String) result[1]);
            return produto;
        }catch (Exception ex) {
            return null;
        }
    }


    public List<Produto> listarProdutosPorColaborador(Integer id) {
        try {
            log.info("Listando produtos por colaborador id {}", id);
            List<Object[]> result = entityManager
                    .createNativeQuery("SELECT * FROM Produto p WHERE p.colaborador_id = :colaboradorId")
                    .setParameter("colaboradorId", id)
                    .getResultList();

            List<Produto> produtos = result.stream()
                    .map(item -> {
                        Produto produto = new Produto();
                        produto.setId((Integer) item[0]);
                        produto.setNomeItem((String) item[1]);
                        return produto;
                    })
                    .collect(Collectors.toList());
            return produtos;
        }catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public Produto buscarProdutoPorNome(String nomeItem) {
        try {
            log.info("Buscando produto por nome {}", nomeItem);
            Object[] result = (Object[]) entityManager
                    .createNativeQuery("SELECT * FROM Produto p WHERE p.nome_item = :nomeItem")
                    .setParameter("nomeItem", nomeItem)
                    .getSingleResult();

            Produto produto = new Produto();
            produto.setId((Integer) result[0]);
            produto.setNomeItem((String) result[1]);
            return produto;
        }catch (Exception ex) {
            return null;
        }
    }

    @Transactional
    public Produto inserirProduto(Produto produto, Integer colaboradorId) {
        log.info("Inserindo produto de nome {}", produto.getNomeItem());
        produto.setId(getProdutoMaxId() + 1);
        int result = entityManager
                .createNativeQuery("INSERT INTO Produto (id, nome_item, colaborador_id) VALUES (?, ?, ?)")
                .setParameter(1, produto.getId())
                .setParameter(2, produto.getNomeItem())
                .setParameter(3, colaboradorId)
                .executeUpdate();

        return produto;
    }

    @Transactional
    public Produto atualizarProduto(Produto produto) {
        log.info("Inserindo produto de nome {}", produto.getNomeItem());
        int result = entityManager
                .createNativeQuery("UPDATE Produto SET nome_item = ? WHERE id = ?")
                .setParameter(1, produto.getNomeItem())
                .setParameter(2, produto.getId())
                .executeUpdate();

        return produto;
    }

    @Transactional
    public void removerProduto(Integer id) {
        log.info("Removendo produto por id {}", id);
        entityManager
                .createNativeQuery("DELETE FROM Produto WHERE id = ?")
                .setParameter(1, id)
                .executeUpdate();
    }

    private Integer getProdutoMaxId() {
        try {
            Integer result = (Integer) entityManager
                    .createNativeQuery("SELECT MAX(id) FROM Produto p")
                    .getSingleResult();
            return Optional.ofNullable(result).orElse(0);
        }catch (Exception ex) {
            log.error("Error", ex);
            return 0;
        }
    }
}
