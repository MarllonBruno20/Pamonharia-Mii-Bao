package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.controller.ClienteDAO
import br.com.marllon.pamonhasmiibao.model.banco.InsercaoBancoException
import br.com.marllon.pamonhasmiibao.R
import br.com.marllon.pamonhasmiibao.model.Cliente

class AlterarDadosClienteActivity : AppCompatActivity() {

    lateinit var tv_cpfCliente : TextView
    lateinit var et_alterarNome : EditText
    lateinit var et_alterarEmail : EditText
    lateinit var et_alterarTelefone : EditText
    lateinit var btn_salvarAlteracoesCliente : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_dados_cliente)

        tv_cpfCliente = findViewById(R.id.textView_alteraCpfCliente)
        et_alterarNome = findViewById(R.id.editText_alterar_dados_nome)
        et_alterarEmail = findViewById(R.id.editText_alterar_dados_email)
        et_alterarTelefone = findViewById(R.id.editText_alterar_dados_telefone)
        btn_salvarAlteracoesCliente = findViewById(R.id.btn_salvaAlteracoesCliente)

        intent.extras?.let {
            et_alterarNome.setText(it.getString("nomeCliente"))
            tv_cpfCliente.text = it.getString("cpfCliente")
            et_alterarTelefone.setText(it.getString("telefoneCliente"))
            et_alterarEmail.setText(it.getString("emailCliente"))
        }

        btn_salvarAlteracoesCliente.setOnClickListener {
            atualizarCliente()
            finish()
        }

    }

    private fun atualizarCliente() {
        try {
            val cpf = tv_cpfCliente.text.toString()
            val nome = et_alterarNome.text.toString()
            val email = et_alterarEmail.text.toString()
            val telefone = et_alterarTelefone.text.toString()

            val cliente = Cliente(cpf, nome, email, telefone)
            val clienteDAO = ClienteDAO(this)

            val inseriu = clienteDAO.alterar(cliente)
            if (inseriu) {
                Toast.makeText(this, "Cliente alterado com sucesso!", Toast.LENGTH_LONG).show()
            }
        } catch (e: InsercaoBancoException) {
            Toast.makeText(this, e.message ?: "Erro ao atualizar cliente.", Toast.LENGTH_LONG).show()
        }
    }

}