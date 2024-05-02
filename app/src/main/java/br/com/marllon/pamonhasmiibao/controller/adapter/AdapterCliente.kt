package br.com.marllon.pamonhasmiibao.controller.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.marllon.pamonhasmiibao.controller.ClienteDAO
import br.com.marllon.pamonhasmiibao.R
import br.com.marllon.pamonhasmiibao.model.Cliente
import br.com.marllon.pamonhasmiibao.view.AlterarDadosClienteActivity

class AdapterCliente(private val context : Context, private val clientes: MutableList<Cliente>): RecyclerView.Adapter<AdapterCliente.ClienteViewHolder>() {

    //criar os itens, os clientes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val itemLista =
            LayoutInflater.from(context).inflate(R.layout.card_view_clientes, parent, false)
        val holder = ClienteViewHolder(itemLista)
        return holder
    }

    override fun getItemCount(): Int = clientes.size

    //responsável por mostrar os itens
    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]

        holder.nome.text = cliente.nome
        holder.cpf.text = cliente.cpf
        holder.telefone.text = cliente.telefone
        holder.email.text = cliente.email
        holder.situacao.text = cliente.situacao

        if (cliente.situacao == "ATIVO") {
            holder.situacao.setTextColor(Color.GREEN)
        } else if (cliente.situacao == "INATIVO") {
            holder.situacao.setTextColor(Color.RED)
        }
    }


    private fun onButtonClickAlterar(position: Int, context: Context) {
        val cliente = clientes[position]

        val intent = Intent(context, AlterarDadosClienteActivity::class.java)
        val bundle = Bundle()
        bundle.putString("nomeCliente", cliente.nome)
        bundle.putString("cpfCliente", cliente.cpf)
        bundle.putString("telefoneCliente", cliente.telefone)
        bundle.putString("emailCliente", cliente.email)
        intent.putExtras(bundle)
        if (context is Activity) {
            context.startActivity(intent)
        }

        notifyItemChanged(position)
    }

    private fun onButtonClickExcluir(position: Int) {
        val cliente = clientes[position]

        val clienteDAO = ClienteDAO(context)
        val desativou = clienteDAO.desativarCliente(cliente)

        if (desativou) {
            cliente.situacao = "INATIVO"
            clientes[position] = cliente
            notifyItemChanged(position)
            Toast.makeText(context, "Cliente desativado com sucesso!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Erro ao desativar o cliente.", Toast.LENGTH_LONG).show()
        }
    }

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome = itemView.findViewById<TextView>(R.id.textView_adapterNome)
        val cpf = itemView.findViewById<TextView>(R.id.textView_adapterCpf)
        val telefone = itemView.findViewById<TextView>(R.id.textView_adapterTelefone)
        val email = itemView.findViewById<TextView>(R.id.textView_adapterEmail)
        val situacao = itemView.findViewById<TextView>(R.id.textView_adapterSituacao)
        val buttonAlterar = itemView.findViewById<Button>(R.id.button_adapterEditar)
        val buttonExcluir = itemView.findViewById<Button>(R.id.button_adapterExcluir)

        init {
            buttonAlterar.setOnClickListener {
                onButtonClickAlterar(adapterPosition, context)
            }

            buttonExcluir.setOnClickListener{
                onButtonClickExcluir(adapterPosition)
            }

        }

    }
}