$(function() {

	carregarItens();

	function carregarItens() {
		$.getJSON("/api/cafe", function(dados) {
			$(dados).each(function(index) {
				$('#tabela_itens').append('<tr><td>' + index.id + '</td>'
					+ '<td>' + index.coloborador_id + '<td>'
					+ index.nome_item + '</td></tr>')
			});
		});
	}

});

$.getJSON('cafe', 
			(response) => {
				console.log(response)
			})