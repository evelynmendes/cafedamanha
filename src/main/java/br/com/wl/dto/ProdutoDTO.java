package br.com.wl.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.wl.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

	@NotNull(message = "Campo obrigatório")
	private Integer idColaborador;

	@NotBlank(message = "Campo obrigatório")
	private String nomeItem;	

	public ProdutoDTO(Produto x) {
		this.nomeItem = x.getNomeItem();
	}

}
