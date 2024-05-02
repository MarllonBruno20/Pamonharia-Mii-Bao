package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.model.banco.InsercaoBancoException
import br.com.marllon.pamonhasmiibao.controller.PedidoPamonhaDAO
import br.com.marllon.pamonhasmiibao.R

class AlterarDadosPedidoActivity : AppCompatActivity() {

    lateinit var tv_idPedido: TextView
    lateinit var tv_cpfCliente: TextView
    lateinit var et_ingredientes : EditText
    lateinit var et_quantidadeQueijo : EditText
    lateinit var btn_alterarDadosPedido: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_dados_pedido)

        tv_idPedido = findViewById(R.id.textView_idPedido)
        tv_cpfCliente = findViewById(R.id.textView_cpfClientePedido)
        et_ingredientes = findViewById(R.id.editText_ingredientes)
        et_quantidadeQueijo = findViewById(R.id.editText_quantidadeQueijo)
        btn_alterarDadosPedido = findViewById(R.id.btn_alterarDadosPedido)

        intent.extras?.let {
            tv_idPedido.text = it.getString("idPedido")
            tv_cpfCliente.text = it.getString("cpfCliente")
            et_ingredientes.setText(it.getString("ingredientesPedido"))
            et_quantidadeQueijo.setText(it.getString("pesoQueijo"))
        }

        btn_alterarDadosPedido.setOnClickListener {
            atualizarPedido()
            finish()
        }

    }

    private fun atualizarPedido() {
        try {
            val idPedido = tv_idPedido.text.toString().toInt()
            val ingredientes = et_ingredientes.text.toString()
            val quantidadeQueijo = et_quantidadeQueijo.text.toString().toDouble()

            val pedidoDao = PedidoPamonhaDAO(this)

            val inseriu = pedidoDao.atualizar(idPedido, ingredientes, quantidadeQueijo)
            if (inseriu > 0){
                Toast.makeText(this, "Pedido alterado com sucesso!", Toast.LENGTH_LONG).show()
            }
        } catch (e: InsercaoBancoException) {
            Toast.makeText(this, e.message ?: "Erro ao atualizar pedido.", Toast.LENGTH_LONG).show()
        }
    }

}