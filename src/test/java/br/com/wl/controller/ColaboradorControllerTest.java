package br.com.wl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wl.CafedamanhaApplicationTests;
import br.com.wl.dto.ColaboradorDTO;
import br.com.wl.errorhandler.GlobalExceptionHandler;
import br.com.wl.model.Colaborador;
import br.com.wl.repository.ColaboradorRepository;
import br.com.wl.repository.ProdutoRepository;
import br.com.wl.service.ColaboradorService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CafedamanhaApplicationTests.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ColaboradorControllerTest {

	@MockBean
	private EntityManager manager;
	
	private ColaboradorController colaboradorController;

	private ObjectMapper mapper = new ObjectMapper();

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		ProdutoRepository produtoRepository =  new ProdutoRepository(manager);
		ColaboradorRepository colaboradorRepository = new ColaboradorRepository(manager,produtoRepository);
		ColaboradorService colaboradorService = new ColaboradorService(colaboradorRepository);
		colaboradorController = new ColaboradorController(colaboradorService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(colaboradorController)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();

	}




	@Test
	public void whenValidInput_thenReturnsSucess() throws Exception {
		ColaboradorDTO dto = new ColaboradorDTO();
	    dto.setCpf("12345678901");
	    dto.setNome("Jose Alguem");

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1);
		colaborador.setCpf(dto.getCpf());
		colaborador.setNome(dto.getNome());
		
		Query query = Mockito.mock(Query.class);

		Mockito.when(manager.createNativeQuery(ArgumentMatchers.any())).thenReturn(query);
		Mockito.when(query.setParameter(ArgumentMatchers.anyInt(),ArgumentMatchers.any())).thenReturn(query,query,query);
		Mockito.when(query.executeUpdate()).thenReturn(1);

	    String json = mapper.writeValueAsString(dto);
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/colaboradores")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.characterEncoding("utf-8")
	            .content(json).accept(MediaType.APPLICATION_JSON))
	    		.andReturn();

	    int status = result.getResponse().getStatus();
	    assertEquals(201, status,"Status inváĺido");

	}

	@Test
	public void whenCharacterSpecialInCpfThenReturnInvalid() throws Exception {
		ColaboradorDTO dto = new ColaboradorDTO();
	    dto.setCpf("123.456.789-01");
	    dto.setNome("Jose Alguem");

	    String json = mapper.writeValueAsString(dto);
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/colaboradores")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.characterEncoding("utf-8")
	            .content(json).accept(MediaType.APPLICATION_JSON))
	    		.andReturn();

	    int status = result.getResponse().getStatus();
	    assertEquals(400, status,"Status inváĺido");
	}

	@Test
	public void whenCpfIncompletThenReturnInvalid() throws Exception {
		ColaboradorDTO dto = new ColaboradorDTO();
	    dto.setCpf("123");
	    dto.setNome("Jose Alguem");

	    String json = mapper.writeValueAsString(dto);
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/colaboradores")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.characterEncoding("utf-8")
	            .content(json).accept(MediaType.APPLICATION_JSON))
	    		.andReturn();

	    int status = result.getResponse().getStatus();
	    assertEquals(400, status,"Status inváĺido");
	  }

	@Test
	public void whenCpfNullThenReturnInvalid() throws Exception {
		ColaboradorDTO colaborador = new ColaboradorDTO();
	    colaborador.setNome("Jose Alguem");

	    String json = mapper.writeValueAsString(colaborador);
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/colaboradores")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.characterEncoding("utf-8")
	            .content(json).accept(MediaType.APPLICATION_JSON))
	    		.andReturn();

	    int status = result.getResponse().getStatus();
	    assertEquals(400, status,"Status inváĺido");
	  }

	@Test
	public void whenThrowDB() throws Exception {
		ColaboradorDTO dto = new ColaboradorDTO();
	    dto.setCpf("12345678901");
	    dto.setNome("Jose Alguem");

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1);
		colaborador.setCpf(dto.getCpf());
		colaborador.setNome(dto.getNome());
		
		Query query = Mockito.mock(Query.class);

		Mockito.when(manager.createNativeQuery(ArgumentMatchers.any())).thenReturn(query);
		Mockito.when(query.setParameter(ArgumentMatchers.anyInt(),ArgumentMatchers.any())).thenReturn(query,query,query);
		Mockito.when(query.executeUpdate()).thenThrow(new RuntimeException("Erro"));

	    String json = mapper.writeValueAsString(dto);
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/colaboradores")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.characterEncoding("utf-8")
	            .content(json).accept(MediaType.APPLICATION_JSON))
	    		.andReturn();

	    int status = result.getResponse().getStatus();
	    assertEquals(500, status,"Status inváĺido");

	}

}