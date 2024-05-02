package br.com.marllon.pamonhasmiibao.controller

import android.content.ContentValues
import android.content.Context
import br.com.marllon.pamonhasmiibao.model.banco.InsercaoBancoException
import br.com.marllon.pamonhasmiibao.model.banco.BancoPamonharia
import br.com.marllon.pamonhasmiibao.model.PedidoPamonha

class PedidoPamonhaDAO (context : Context) {

    val meuBanco = BancoPamonharia(context)
    val clienteDAO = ClienteDAO(context)

    fun inserirPedidoPamonha(cpfCliente: String, tipoDeRecheio: String, pesoDoQueijo: Double): Boolean {
        val db = meuBanco.writableDatabase

        try {
            val cliente = clienteDAO.buscaClientePorCpf(cpfCliente)

            if (cliente != null) {
                if (cliente.situacao == "INATIVO") {
                    throw InsercaoBancoException("Cliente inativo. Pedido não pode ser inserido.")
                } else {
                    val cv = ContentValues().apply {
                        put("fk_cpf", cpfCliente)
                        put("tipoDeRecheio", tipoDeRecheio)
                        put("pesoDeQueijo", pesoDoQueijo)
                    }

                    val resultado = db.insert("PedidoPamonha", null, cv)

                    if (resultado == -1L) {
                        db.endTransaction()
                        throw InsercaoBancoException("Erro ao inserir pedido no banco de dados.")
                    }

                    return true
                }
            }
            return false
        } catch (e: Exception) {
            throw InsercaoBancoException("Erro ao inserir pedido no banco de dados: ${e.message}")
        } finally {
            db.close()
        }
    }


    fun listar(): MutableList<PedidoPamonha> {
        val lista = mutableListOf<PedidoPamonha>()
        val db = meuBanco.readableDatabase
        val sql = "SELECT * FROM PedidoPamonha"
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("idPedidoPamonha"))
                val cpf = cursor.getString(cursor.getColumnIndexOrThrow("fk_cpf"))
                val tipoDeRecheio = cursor.getString(cursor.getColumnIndexOrThrow("tipoDeRecheio"))
                val pesoDoQueijo = cursor.getDouble(cursor.getColumnIndexOrThrow("pesoDeQueijo"))
                val pedidoPamonha = PedidoPamonha(id, cpf, tipoDeRecheio, pesoDoQueijo)
                lista.add(pedidoPamonha)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    fun atualizar(idPedido: Int, tipoDeRecheio: String, pesoDoQueijo: Double): Int {
        val db = meuBanco.writableDatabase
        val cv = ContentValues()
        cv.put("tipoDeRecheio", tipoDeRecheio)
        cv.put("pesoDeQueijo", pesoDoQueijo)

        try {
            val linhasAfetadas = db.update("PedidoPamonha", cv, "idPedidoPamonha = ?", arrayOf(idPedido.toString()))
            return linhasAfetadas
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        } finally {
            db.close()
        }
    }

    fun excluir(idPedido: Int): Int {
        val db = meuBanco.writableDatabase
        val linhasAfetadas = db.delete("PedidoPamonha", "idPedidoPamonha = ?", arrayOf(idPedido.toString()))
        db.close()
        return linhasAfetadas
    }

}