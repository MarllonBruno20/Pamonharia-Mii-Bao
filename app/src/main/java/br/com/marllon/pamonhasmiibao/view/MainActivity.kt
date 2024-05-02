package br.com.marllon.pamonhasmiibao.view

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.controller.ClienteDAO
import br.com.marllon.pamonhasmiibao.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val caminhoDoArquivo = "MeuArquivo"
    private var arquivoExterno: File?=null

    lateinit var btn_cliente: Button
    lateinit var btn_pedido: Button
    lateinit var btn_backup : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_cliente = findViewById(R.id.btn_cliente)
        btn_pedido = findViewById(R.id.btn_pedido)
        btn_backup = findViewById(R.id.btn_backupBanco)

        btn_cliente.setOnClickListener {
            val intent = Intent(this, EscolhaClienteActivity::class.java)
            startActivity(intent)
        }
        btn_pedido.setOnClickListener {
            val intent = Intent(this, EscolhaPedidoActivity::class.java)
            startActivity(intent)
        }

        btn_backup.setOnClickListener {

            var clienteDao = ClienteDAO(this)
            val dados = clienteDao.exportarDados()

            val nomeDoArquivo = "meuArquivoDeTexto.txt"
            arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo), nomeDoArquivo)

            try {
                val fileOutputStream = FileOutputStream(arquivoExterno)
                fileOutputStream.write(dados.toByteArray())
                fileOutputStream.close()

                val intent = Intent(this, BackupActivity::class.java)
                intent.putExtra("caminhoArquivo", arquivoExterno!!.absolutePath)
                startActivity(intent)

                Toast.makeText(applicationContext, "Texto salvo com sucesso!", Toast.LENGTH_LONG)
                    .show()
            } catch (e: IOException) {
                Log.i("Erro", "Erro ao salvar o arquivo: ${e.message}")
                e.printStackTrace()
            }

        }

    }

}