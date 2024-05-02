package br.com.marllon.pamonhasmiibao.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.R

class EscolhaPedidoActivity : AppCompatActivity() {

    lateinit var btn_realizarPedido : Button
    lateinit var btn_listarPedidos : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escolha_pedido)

        btn_realizarPedido = findViewById(R.id.btn_realizarPedido)
        btn_listarPedidos = findViewById(R.id.btn_listarPedidos)

        btn_realizarPedido.setOnClickListener {
            val intent = Intent(this, PedidoPamonhaActivity::class.java)
            startActivity(intent)
        }

        btn_listarPedidos.setOnClickListener {
            val intent = Intent(this, ListagemPedidosActivity::class.java)
            startActivity(intent)
        }

    }
}