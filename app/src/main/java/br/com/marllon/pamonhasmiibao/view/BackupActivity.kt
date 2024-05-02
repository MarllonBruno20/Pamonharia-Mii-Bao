package br.com.marllon.pamonhasmiibao.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.marllon.pamonhasmiibao.R
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

class BackupActivity : AppCompatActivity() {

    lateinit var tv_backup : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)

        val caminhoArquivo = intent.getStringExtra("caminhoArquivo")

        if (caminhoArquivo != null) {
            abrirArquivo(caminhoArquivo)
        } else {
            Toast.makeText(applicationContext, "Caminho do arquivo não fornecido!", Toast.LENGTH_LONG).show()
        }
    }

    private fun abrirArquivo(caminhoArquivo: String) {
        val arquivo = File(caminhoArquivo)
        try {
            val inputStream = FileInputStream(arquivo)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val stringBuilder = StringBuilder()
            var linha: String? = bufferedReader.readLine()
            while (linha != null) {
                stringBuilder.append(linha).append("\n")
                linha = bufferedReader.readLine()
            }

            bufferedReader.close()
            inputStreamReader.close()
            inputStream.close()

            val textViewConteudoBackup = findViewById<TextView>(R.id.textoBackup)
            textViewConteudoBackup.text = stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Erro ao ler o arquivo!", Toast.LENGTH_LONG).show()
        }
    }

}