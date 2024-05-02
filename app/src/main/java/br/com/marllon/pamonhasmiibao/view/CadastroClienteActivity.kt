package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.model.Cliente
import br.com.marllon.pamonhasmiibao.controller.ClienteDAO
import br.com.marllon.pamonhasmiibao.model.banco.InsercaoBancoException
import br.com.marllon.pamonhasmiibao.R

class CadastroClienteActivity : AppCompatActivity() {

    lateinit var editTextNome: android.widget.EditText
    lateinit var editTextCpf: android.widget.EditText
    lateinit var editTextEmail: android.widget.EditText
    lateinit var editTextTelefone: android.widget.EditText
    lateinit var btnCadastrar: android.widget.Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        editTextNome = findViewById(R.id.editText_nome)
        editTextCpf = findViewById(R.id.editText_cpf)
        editTextEmail = findViewById(R.id.editText_email)
        editTextTelefone = findViewById(R.id.editText_telefone)
        btnCadastrar = findViewById(R.id.btn_cadastrar)

        btnCadastrar.setOnClickListener {
            cadastrarCliente()
        }
    }

    private fun cadastrarCliente() {
        try {
            val cpf = editTextCpf.text.toString()
            val nome = editTextNome.text.toString()
            val email = editTextEmail.text.toString()
            val telefone = editTextTelefone.text.toString()

            val cliente = Cliente(cpf, nome, email, telefone)
            val clienteDAO = ClienteDAO(this)

            val inseriu = clienteDAO.inserirCliente(cliente)
            if (inseriu) {
                limparCampos()
                Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG).show()
            }
        } catch (e: InsercaoBancoException) {
            Toast.makeText(this, e.message ?: "Erro ao cadastrar cliente.", Toast.LENGTH_LONG).show()
        }
    }

    private fun limparCampos() {
        editTextCpf.text.clear()
        editTextNome.text.clear()
        editTextEmail.text.clear()
        editTextTelefone.text.clear()
    }

}