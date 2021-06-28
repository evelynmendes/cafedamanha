$(document).ready(function () {
	
	$(#onload).onload(function (e) {
		$.get('https://locahost:8080/cafe/consultar',$(this).serialize(), function (lista){
			$('tabela_itens').last().append(
				'<tr>' +
				  '<td>' + ${lista.id} + '</td>' +
		          '<td>' + ${lista.colaborador} + '</td>' +
		          '<td>' + ${lista.nome_item} + '</td>' +	
				'</tr>'
			);
		
		});
		
		clearInputs();
		e.preventDefault();
	});
});