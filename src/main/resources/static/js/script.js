function carregar_cafe_da_manha() {
    $.get("/api/colaborador").done(function(dados) {
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
    $.get("/api/colaborador").done(function(dados) {
        console.log(dados);
        $.each(dados, function(colaborador) {
            console.log(this);
            $('#tabela_itens').append('<tr>');
            $('#tabela_itens').append('<td>' + this.id + '</td>');
            $('#tabela_itens').append('<td>' + this.nome + '</td>');
            $('#tabela_itens').append('<td>' + this.cpf + '</td>');
            $('#tabela_itens').append('<td><button onclick="atualizar_colaborador(' + this.id + ')">Atualizar</button></td>');
            $('#tabela_itens').append('<td><button onclick="remover_colaborador(' + this.id + ')">Remover</button></td>');
            $('#tabela_itens').append('</tr>');
        });
    });
}

function atualizar_colaborador(id) {
    window.location.href = "/colaborador/" + id;
}

function remover_colaborador(id) {
    alert("remover colaborador " + id);
}

function carregar_produtos() {
    alert("carregar produtos");
}

function cadastrar(){
    var nome = $("#txtNome").text();
    var cpf = $("#txtCPF").text();
    var colaborador = {
        nome: nome,
        cpf: cpf
    };
    $.post("/api/colaborador", colaborador).done(function(dados) {
        alert("Incluido com sucesso");
    }).fail(function(data) {
        alert("error");
    });
}