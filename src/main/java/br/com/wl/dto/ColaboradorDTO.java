package br.com.wl.dto;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.wl.model.Colaborador;
import br.com.wl.model.ListaCafe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColaboradorDTO {

	@NotBlank
	@Pattern(regexp = "^([0-9]{11})$")
	private String cpf;

	@NotBlank
	private String nome;

	public ColaboradorDTO(Colaborador x) {
		this.cpf = x.getCpf();
		this.nome = x.getNome();		
	}


}
