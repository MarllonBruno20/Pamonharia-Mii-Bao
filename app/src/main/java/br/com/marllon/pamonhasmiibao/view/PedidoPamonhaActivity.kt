package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.model.banco.InsercaoBancoException
import br.com.marllon.pamonhasmiibao.controller.PedidoPamonhaDAO
import br.com.marllon.pamonhasmiibao.R

class PedidoPamonhaActivity : AppCompatActivity() {

    lateinit var editTextCpfPedido : EditText
    lateinit var editTextTipoRecheio: EditText
    lateinit var editTextQuantidadeQueijo: EditText
    lateinit var btnRealizarPedido: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_pamonha)

        editTextCpfPedido = findViewById(R.id.editText_cpfPedidoPamonha)
        editTextTipoRecheio = findViewById(R.id.editText_tipoRecheioPamonha)
        editTextQuantidadeQueijo = findViewById(R.id.editText_quantidadeQueijoPamonha)
        btnRealizarPedido = findViewById(R.id.btn_realizarPedidoPamonha)

        btnRealizarPedido.setOnClickListener {
            realizarPedidoPamonha()
        }
    }

    private fun realizarPedidoPamonha() {

        try {

            val cpf = editTextCpfPedido.text.toString()
            val tipoRecheio = editTextTipoRecheio.text.toString()
            val quantidadeQueijo = editTextQuantidadeQueijo.text.toString().toDoubleOrNull() ?: 0.0

            val pedidoPamonhaDAO = PedidoPamonhaDAO(this)

            val inseriu = pedidoPamonhaDAO.inserirPedidoPamonha(cpf, tipoRecheio, quantidadeQueijo)
            if (inseriu) {
                limparCampos()
                Toast.makeText(this, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Erro ao realizar pedido, cliente não encontrado.", Toast.LENGTH_LONG).show()
            }

        } catch (e: InsercaoBancoException) {
            e.printStackTrace()
            Toast.makeText(this, e.message ?: "Erro ao realizar pedido.", Toast.LENGTH_LONG).show()
        }
    }

    private fun limparCampos() {
        editTextCpfPedido.text.clear()
        editTextTipoRecheio.text.clear()
        editTextQuantidadeQueijo.text.clear()
    }
}