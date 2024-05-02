package br.com.marllon.pamonhasmiibao.model

data class Cliente (var cpf : String, var nome : String, var email : String, var telefone : String,
                                var situacao : String) {


    constructor(cpf : String, nome : String, email : String, telefone : String) : this(cpf, nome, email, telefone, "ATIVO")
}