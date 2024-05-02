package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marllon.pamonhasmiibao.controller.ClienteDAO
import br.com.marllon.pamonhasmiibao.R
import br.com.marllon.pamonhasmiibao.controller.adapter.AdapterCliente

class ListagemClientesActivity : AppCompatActivity() {

    lateinit var button_adapterEditar : Button
    lateinit var button_adapterExcluir : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_clientes)

        val recyclerviewClientes = findViewById<RecyclerView>(R.id.recyclerViewClientes)
        recyclerviewClientes.layoutManager = LinearLayoutManager(this)
        recyclerviewClientes.setHasFixedSize(true)

        val clienteDao = ClienteDAO(this)

        val adapterCliente = AdapterCliente(this, clienteDao.listar())
        recyclerviewClientes.adapter = adapterCliente


    }
}