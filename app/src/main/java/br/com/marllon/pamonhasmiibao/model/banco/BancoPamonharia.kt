package br.com.marllon.pamonhasmiibao.model.banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BancoPamonharia(context : Context) : SQLiteOpenHelper(context, "BancoMIIBAO.db", null, 1){

    companion object {
        const val NOME_TABELA_CLIENTE = "Cliente"
        const val NOME = "nome"
        const val CPF = "cpf"
        const val TELEFONE = "telefone"
        const val EMAIL = "email"
        const val SITUACAO = "situacao"

        const val NOME_TABELA_PEDIDO_PAMONHA = "PedidoPamonha"
        const val ID_PEDIDO_PAMONHA = "idPedidoPamonha"
        const val TIPO_DE_RECHEIO = "tipoDeRecheio"
        const val PESO_DE_QUEIJO = "pesoDeQueijo"
        const val FK_CPF_CLIENTE = "fk_cpf"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val sqlCriacaoCliente = "CREATE TABLE $NOME_TABELA_CLIENTE (" +
                "$CPF INTEGER PRIMARY KEY," +
                "$TELEFONE TEXT," +
                "$EMAIL TEXT," +
                "$NOME TEXT," +
                "$SITUACAO TEXT default 'ATIVO')"

        val sqlCriacaoPedidoPamonha = "CREATE TABLE $NOME_TABELA_PEDIDO_PAMONHA (" +
                "$ID_PEDIDO_PAMONHA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TIPO_DE_RECHEIO TEXT NOT NULL," +
                "$PESO_DE_QUEIJO REAL NOT NULL," +
                "$FK_CPF_CLIENTE INTEGER NOT NULL," +
                "FOREIGN KEY($FK_CPF_CLIENTE) REFERENCES $NOME_TABELA_CLIENTE($CPF))"


        db.execSQL(sqlCriacaoCliente)
        db.execSQL(sqlCriacaoPedidoPamonha)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql_exclusaoTabelaCliente = "DROP TABLE IF EXISTS $NOME_TABELA_CLIENTE"
        val sql_exclusaoTabelaPedidoPamonha = "DROP TABLE IF EXISTS $NOME_TABELA_PEDIDO_PAMONHA"
        db.execSQL(sql_exclusaoTabelaCliente)
        db.execSQL(sql_exclusaoTabelaPedidoPamonha)

        onCreate(db)
    }

}