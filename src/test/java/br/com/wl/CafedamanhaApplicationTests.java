package br.com.wl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import br.com.wl.repository.ColaboradorRepository;
import br.com.wl.repository.ProdutoRepository;

@SpringBootTest
@TestPropertySource(properties =
		"spring.autoconfigure.exclude=" +
				"  org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration, " +
				"  org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration, " +
				"  org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration")
public class CafedamanhaApplicationTests {

	@MockBean
	private ColaboradorRepository colaboradorRepository;

	@MockBean
	private ProdutoRepository produtoRepository;

	@Test
	void contextLoads() {
	}

}
