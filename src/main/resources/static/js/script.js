function carregar_cafe_da_manha() {
    $.get("/api/colaboradores").done(function(dados) {
        console.log(dados);
        $.each(dados, function(colaborador) {
            console.log(this);
            for (let i = 0; i < this.lista.length; i++) {
                $('#tabela_itens').append('<tr>');
                $('#tabela_itens').append('<td>' + this.id + '</td>');
                $('#tabela_itens').append('<td>' + this.nome + '</td>');
                $('#tabela_itens').append('<td>' + this.produtos[i].nomeItem + '</td>');
                $('#tabela_itens').append('</tr>');
            }
        });
    });
}

function carregar_colaboradores() {
    $.get("/api/colaboradores").done(function(dados) {
        console.log(dados);
        $.each(dados, function(index) {
            console.log(this);
            $('#tabela_itens').append('<tr>');
            $('#tabela_itens').append('<td>' + this.id + '</td>');
            $('#tabela_itens').append('<td>' + this.nome + '</td>');
            $('#tabela_itens').append('<td>' + this.cpf + '</td>');
            $('#tabela_itens').append('<td><button onclick="listar_produtos(' + this.id + ')">Produtos</button></td>');
            $('#tabela_itens').append('<td><button onclick="atualizar_colaborador(' + this.id + ')">Atualizar</button></td>');
            $('#tabela_itens').append('<td><button onclick="remover_colaborador(' + this.id + ')">Remover</button></td>');
            $('#tabela_itens').append('</tr>');
        });
    });
}

function listar_produtos(id) {
    window.location.href = "/colaboradores/" + id;
}

function atualizar_colaborador(id) {
    window.location.href = "/colaboradores/" + id;
}

function remover_colaborador(id) {
    $.ajax({
        url: '/api/colaboradores/' + id,
        type: 'DELETE',
        success: function(result) {
            alert("Colaborador excluído com sucesso");
            window.location.href = "/colaboradores";
        },
        fail: function(result) {
            alert("Falha ao remover o produto");
        }
    });
}

function carregar_produtos(id) {
    $.get("/api/colaboradores/" + id).done(function(colaborador) {
        console.log(colaborador);
        $("#colaborador").text(colaborador.nome);
        for (var i=0; i< colaborador.lista.length; i++) {
            $('#tabela_itens').append('<tr>');
            $('#tabela_itens').append('<td>' + colaborador.lista[i].id + '</td>');
            $('#tabela_itens').append('<td>' + colaborador.lista[i].nomeItem + '</td>');
            $('#tabela_itens').append('<td><button onclick="atualizar_produto('+ colaborador.lista[i].id + ')">Atualizar</button></td>');
            $('#tabela_itens').append('<td><button onclick="remover_produto(' + colaborador.id + ',' + colaborador.lista[i].id + ')">Remover</button></td>');
            $('#tabela_itens').append('</tr>');
        }
    });
}

function atualizar_produto(id) {
    alert("atualizar produto " + id);
}

function remover_produto(colaboradorId, produtoId) {
    $.ajax({
        url: '/api/produtos/' + produtoId,
        type: 'DELETE',
        success: function(result) {
            alert("Produto excluído com sucesso");
            window.location.href = "/colaboradores/" + colaboradorId;
        },
        fail: function(result) {
            alert("Falha ao remover o produto");
        }
    });
}

function cadastrar_colaborador(){
    console.log("Cadastrando colaborador");
    var nome = $("#nome").val();
    var cpf = $("#cpf").val();
    var colaborador = {
        nome: nome,
        cpf: cpf
    };
    $.ajax({
        url: '/api/colaboradores',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(colaborador),
        success: function(result) {
            alert("Colaborador incluído com sucesso");
            window.location.href = "/colaboradores";
        },
        fail: function(jqXHR, textStatus) {
            console.log(jqXHR);
            alert("Falha ao incluir colaborador");
        }
    });
    return false;
}

function cadastrar_produto(){
    console.log("Cadastrando produto");
    var nome = $("#nome").val();
    var id = $("#colaboradorId").val();
    var produto = {
        nomeItem: nome,
        idColaborador: id
    };
    $.ajax({
        url: '/api/produtos',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(produto),
        success: function(result) {
            alert("Colaborador incluído com sucesso");
            window.location.href = "/colaboradores";
        },
        fail: function(jqXHR, textStatus) {
            console.log(jqXHR);
            alert("Falha ao incluir colaborador");
        }
    });
    return false;
}