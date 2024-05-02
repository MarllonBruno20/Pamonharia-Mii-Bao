package br.com.marllon.pamonhasmiibao.controller.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.marllon.pamonhasmiibao.controller.PedidoPamonhaDAO
import br.com.marllon.pamonhasmiibao.R
import br.com.marllon.pamonhasmiibao.model.PedidoPamonha
import br.com.marllon.pamonhasmiibao.view.AlterarDadosPedidoActivity

class AdapterPedido (private val context : Context, private val pedidos: MutableList<PedidoPamonha>): RecyclerView.Adapter<AdapterPedido.PedidoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.card_view_pedidos, parent, false)
        val holder = PedidoViewHolder(itemLista)
        return holder
    }

    override fun getItemCount(): Int = pedidos.size

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]

        holder.idPedido.text = pedido.IdPedido.toString()
        holder.cpfCliente.text = pedido.cpfCliente
        holder.tipoDeRecheio.text = pedido.tipoDeRecheio
        holder.pesoDoQueijo.text = buildString {
        append(pedido.pesoDoQueijo)
        append(" g")
    }
    }

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idPedido = itemView.findViewById<TextView>(R.id.textView_adapterIdPedido)
        val cpfCliente = itemView.findViewById<TextView>(R.id.textView_adapterCpfClientePedido)
        val tipoDeRecheio = itemView.findViewById<TextView>(R.id.textView_adapterIngredientesPedido)
        val pesoDoQueijo = itemView.findViewById<TextView>(R.id.textView_adapterTelefone)
        val buttonAlterar = itemView.findViewById<Button>(R.id.button_adapterEditarPedido)
        val buttonExcluir = itemView.findViewById<Button>(R.id.button_adapterExcluirPedido)

        init {
            buttonAlterar.setOnClickListener {
                onButtonClickAlterar(adapterPosition, context)
            }

            buttonExcluir.setOnClickListener{
                onButtonClickExcluir(adapterPosition)
            }

        }

    }

    private fun onButtonClickAlterar(position: Int, context: Context) {
        val pedido = pedidos[position]

        val intent = Intent(context, AlterarDadosPedidoActivity::class.java)
        val bundle = Bundle()
        bundle.putString("idPedido", pedido.IdPedido.toString())
        bundle.putString("cpfCliente", pedido.cpfCliente)
        bundle.putString("ingredientesPedido", pedido.tipoDeRecheio)
        bundle.putString("pesoQueijo", pedido.pesoDoQueijo.toString())
        intent.putExtras(bundle)
        if (context is Activity) {
            context.startActivity(intent)
        }

        notifyItemChanged(position)
    }

    private fun onButtonClickExcluir(position: Int) {
        val pedido = pedidos[position]

        val pedidoDAO = PedidoPamonhaDAO(context)
        var excluiu = pedidoDAO.excluir(pedido.IdPedido)

        if(excluiu > 0){
            pedidos.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, pedidos.size)
            Toast.makeText(context, "Pedido excluido com sucesso!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Erro ao excluir o pedido!", Toast.LENGTH_LONG).show()
        }

    }

}