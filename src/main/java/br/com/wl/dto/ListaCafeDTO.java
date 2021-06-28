package br.com.wl.dto;

import javax.validation.constraints.NotBlank;

import br.com.wl.model.Colaborador;
import br.com.wl.model.ListaCafe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaCafeDTO {


	@NotBlank(message = "Campo obrigatório")
	private String nomeItem;	
	
	@NotBlank(message = "Campo obrigatório")
	private Colaborador lista;
	
	public ListaCafeDTO(ListaCafe x) {
		this.nomeItem = x.getNomeItem();
		this.lista= x.getLista();
	}

}
