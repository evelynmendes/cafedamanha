function carregar_cafe_da_manha() {
    $.get("/api/colaboradores").done(function(dados) {
        console.log(dados);
        $.each(dados, function(colaborador) {
            console.log(this);
            for (var i = 0; i < this.lista.length; i++) {
                $('#tabela_itens').append('<tr>');
                $('#tabela_itens').append('<td>' + this.id + '</td>');
                $('#tabela_itens').append('<td>' + this.nome + '</td>');
                $('#tabela_itens').append('<td>' + this.cpf + '</td>');
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
            $('#tabela_itens').append('<td><button class="btn btn-outline-primary btn-sm" onclick="listar_produtos(' + this.id + ')">Produtos</button></td>');
            $('#tabela_itens').append('<td><button class="btn btn-outline-primary btn-sm" onclick="carregar_atualizar_colaborador(' + this.id + ')">Atualizar</button></td>');
            $('#tabela_itens').append('<td><button class="btn btn-outline-primary btn-sm" onclick="remover_colaborador(' + this.id + ')">Remover</button></td>');
            $('#tabela_itens').append('</tr>');
        });
    });
}

function listar_produtos(id) {
    window.location.href = "/colaboradores/" + id;
}

function carregar_atualizar_colaborador(id) {
    window.location.href = "/colaboradores/" + id + "/atualizar";
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
            $('#tabela_itens').append('<td><button class="btn btn-outline-primary btn-sm" onclick="carregar_atualizar_produto(' + colaborador.id + ',' + colaborador.lista[i].id + ')">Atualizar</button></td>');
            $('#tabela_itens').append('<td><button class="btn btn-outline-primary btn-sm" onclick="remover_produto(' + colaborador.id + ',' + colaborador.lista[i].id + ')">Remover</button></td>');
            $('#tabela_itens').append('</tr>');
        }
    });
}

function carregar_atualizar_produto(colaboradorId, produtoId) {
    window.location.href = "/colaboradores/" + colaboradorId + "/produtos/" + produtoId + "/atualizar";
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
    var colaboradorId = $("#colaboradorId").val();
    var produto = {
        nomeItem: nome,
        idColaborador: colaboradorId
    };
    $.ajax({
        url: '/api/produtos',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(produto),
        success: function(result) {
            alert("Colaborador incluído com sucesso");
            window.location.href = "/colaboradores/" + colaboradorId;
        },
        fail: function(jqXHR, textStatus) {
            console.log(jqXHR);
            alert("Falha ao incluir produto");
        }
    });
    return false;
}

function carregar_colaborador() {
    var colaboradorId = $("#colaboradorId").val();
    $.get("/api/colaboradores/" + colaboradorId).done(function(colaborador) {
        $("#nome").val(colaborador.nome);
        $("#cpf").val(colaborador.cpf);
    });
}

function atualizar_colaborador() {
    console.log("Cadastrando colaborador");
    var colaboradorId = $("#colaboradorId").val();
    var nome = $("#nome").val();
    var cpf = $("#cpf").val();
    var colaborador = {
        nome: nome,
        cpf: cpf
    };
    $.ajax({
        url: '/api/colaboradores/' + colaboradorId,
        type: 'PUT',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(colaborador),
        success: function(result) {
            alert("Colaborador incluído com sucesso");
            window.location.href = "/colaboradores";
        },
        fail: function(jqXHR, textStatus) {
            console.log(jqXHR);
            alert("Falha ao atualizar colaborador");
        }
    });
    return false;
}

function carregar_produto() {
    var produtoId = $("#produtoId").val();
    $.get("/api/produtos/" + produtoId).done(function(produto) {
        $("#nome").val(produto.nomeItem);
    });
}

function atualizar_produto() {
    console.log("Cadastrando produto");
    var nome = $("#nome").val();
    var colaboradorId = $("#colaboradorId").val();
    var produtoId = $("#produtoId").val();
    var produto = {
        nomeItem: nome,
        idColaborador: colaboradorId
    };
    $.ajax({
        url: '/api/produtos/' + produtoId,
        type: 'PUT',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(produto),
        success: function(result) {
            alert("Colaborador incluído com sucesso");
            window.location.href = "/colaboradores/" + colaboradorId;
        },
        fail: function(jqXHR, textStatus) {
            console.log(jqXHR);
            alert("Falha ao incluir produto");
        }
    });
    return false;
}
function onlynumber(evt) {
   var theEvent = evt || window.event;
   var key = theEvent.keyCode || theEvent.which;
   key = String.fromCharCode( key );
   //var regex = /^[0-9.,]+$/;
   var regex = /^[0-9]+$/;
   if( !regex.test(key) ) {
      theEvent.returnValue = false;
      if(theEvent.preventDefault) theEvent.preventDefault();
   }
}