package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.R

class EscolhaClienteActivity : AppCompatActivity() {

    lateinit var btn_cadastrar : Button
    lateinit var btn_listar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escolha_cliente)

        btn_cadastrar = findViewById(R.id.btn_escolhaCadastro)
        btn_listar = findViewById(R.id.btn_listarClientes)

        btn_cadastrar.setOnClickListener {
            val intent = android.content.Intent(this, CadastroClienteActivity::class.java)
            startActivity(intent)
        }

        btn_listar.setOnClickListener {
            val intent = android.content.Intent(this, ListagemClientesActivity::class.java)
            startActivity(intent)
        }

    }
}