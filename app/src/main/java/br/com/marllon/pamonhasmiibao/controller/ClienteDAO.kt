package br.com.marllon.pamonhasmiibao.controller

import android.content.ContentValues
import android.content.Context
import android.util.Log
import br.com.marllon.pamonhasmiibao.model.banco.InsercaoBancoException
import br.com.marllon.pamonhasmiibao.model.banco.BancoPamonharia
import br.com.marllon.pamonhasmiibao.model.Cliente

class ClienteDAO (context : Context) {

    val meuBanco = BancoPamonharia(context)

    fun inserirCliente(cliente: Cliente): Boolean {

        val db = meuBanco.writableDatabase
        val cv = ContentValues()
        cv.put("cpf", cliente.cpf)
        cv.put("nome", cliente.nome)
        cv.put("email", cliente.email)
        cv.put("telefone", cliente.telefone)

        //nullColumnHack -> Sometimes you want to insert an empty row, in that case ContentValues have no content value, and you should use nullColumnHack.
        val resultado = db?.insert("Cliente", null, cv)
        Log.i("Teste", "Inserção: " + resultado)

        if (resultado == -1L) {
            throw InsercaoBancoException("Erro ao inserir cliente no banco de dados.")
        }

        return true
    }


    fun listar(): MutableList<Cliente> {
        val lista = mutableListOf<Cliente>()
        val db = meuBanco.readableDatabase
        try {
            val sql = "SELECT * FROM Cliente"
            val cursor = db.rawQuery(sql, null)
            if (cursor.moveToFirst()) {
                do {
                    val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
                    val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                    val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                    val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))
                    val situacao = cursor.getString(cursor.getColumnIndexOrThrow("situacao"))
                    val cliente = Cliente(cpf, nome, email, telefone, situacao)
                    lista.add(cliente)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("listarClientes", "Erro ao listar clientes", e)

        } finally {
            db.close()
        }
        return lista
    }


    fun alterar(cliente : Cliente) : Boolean{
        val db = meuBanco.writableDatabase
        val cv  = ContentValues()
        cv.put("nome", cliente.nome)
        cv.put("email", cliente.email)
        cv.put("telefone", cliente.telefone)
        val linhasAfetadas = db.update("Cliente", cv, "cpf = ?", arrayOf(cliente.cpf))
        db.close()

        return linhasAfetadas > 0
    }

    fun desativarCliente(cliente: Cliente): Boolean {
        val db = meuBanco.writableDatabase
        val valores = ContentValues()
        valores.put("situacao", "INATIVO")

        val linhasAfetadas = db.update("Cliente", valores, "cpf = ?", arrayOf(cliente.cpf))
        Log.i("Teste", "Update: " + linhasAfetadas)
        db.close()
        return linhasAfetadas > 0
    }

    fun buscaClientePorCpf(cpf: String): Cliente? {
        val db = meuBanco.readableDatabase
        val sql = "SELECT * FROM Cliente WHERE cpf = ?"
        val cursor = db.rawQuery(sql, arrayOf(cpf))
        var cliente: Cliente? = null
        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))
            cliente = Cliente(cpf, nome, email, telefone)
        }
        cursor.close()
        db.close()
        return cliente
    }

    fun exportarDados() : String{
        val db = meuBanco.readableDatabase
        val dados = StringBuilder()

        val cursorClientes = db.rawQuery("SELECT * FROM Cliente", null)

        dados.append("Dados dos usuários: \n\n")
        if (cursorClientes.moveToFirst()) {
            do {
                dados.append("CPF: " + cursorClientes.getString(cursorClientes.getColumnIndexOrThrow("cpf")) + "\n")
                dados.append("Nome: " + cursorClientes.getString(cursorClientes.getColumnIndexOrThrow("nome")) + "\n")
                dados.append("E-mail: " + cursorClientes.getString(cursorClientes.getColumnIndexOrThrow("email")) + "\n")
                dados.append("Telefone: " + cursorClientes.getString(cursorClientes.getColumnIndexOrThrow("telefone")) + "\n")
                dados.append("Situação: " + cursorClientes.getString(cursorClientes.getColumnIndexOrThrow("situacao")) + "\n\n")
            }while(cursorClientes.moveToNext())
        }

        cursorClientes.close()

        val cursorPedidos = db.rawQuery("SELECT * FROM PedidoPamonha", null)

        dados.append("\nDados dos pedidos: \n\n")
        if (cursorPedidos.moveToFirst()) {
            do {
                dados.append("ID do pedido: " + cursorPedidos.getString(cursorPedidos.getColumnIndexOrThrow("idPedidoPamonha")) + "\n")
                dados.append("Ingredientes: " + cursorPedidos.getString(cursorPedidos.getColumnIndexOrThrow("tipoDeRecheio")) + "\n")
                dados.append("Peso de queijo: " + cursorPedidos.getString(cursorPedidos.getColumnIndexOrThrow("pesoDeQueijo")) + "\n")
                dados.append("CPF do cliente: " + cursorPedidos.getString(cursorPedidos.getColumnIndexOrThrow("fk_cpf")) + "\n\n")
            }while(cursorPedidos.moveToNext())
        }

        cursorPedidos.close()
        db.close()
        return dados.toString()

        }

    }
