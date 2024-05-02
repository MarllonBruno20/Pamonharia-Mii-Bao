package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marllon.pamonhasmiibao.controller.PedidoPamonhaDAO
import br.com.marllon.pamonhasmiibao.R
import br.com.marllon.pamonhasmiibao.controller.adapter.AdapterPedido

class ListagemPedidosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_pedidos)

        val recyclerViewPedidos = findViewById<RecyclerView>(R.id.recyclerViewPedidos)
        recyclerViewPedidos.layoutManager = LinearLayoutManager(this)
        recyclerViewPedidos.setHasFixedSize(true)

        val pedidoPamonhaDAO = PedidoPamonhaDAO(this)

        val adapterPedido = AdapterPedido(this, pedidoPamonhaDAO.listar())
        recyclerViewPedidos.adapter = adapterPedido

    }
}