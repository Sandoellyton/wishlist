package com.example.wishlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class FormActivity : AppCompatActivity() {
    private lateinit var eDescricao: EditText
    private lateinit var subNota: SeekBar
    private lateinit var tvNota: TextView
    private lateinit var btnCadastrar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.eDescricao = findViewById(R.id.etDescricao)
        this.subNota = findViewById(R.id.sbNota)
        this.tvNota = findViewById(R.id.tvNota)
        this.btnCadastrar = findViewById(R.id.btnCadastrar)
        this.btnCancelar = findViewById(R.id.btCancelar)

        this.subNota.setOnSeekBarChangeListener(OnChange())

        this.btnCadastrar.setOnClickListener{ cadastrar() }
        this.btnCancelar.setOnClickListener{ finish() }
    }

    fun cadastrar(){
        val descricao = this.eDescricao.text.toString()
        val nota = this.subNota.progress
        val desejo = Desejo(descricao, nota)
        val intent = Intent()
        intent.putExtra("DESEJO", desejo)
        setResult(RESULT_OK, intent)
        finish()
    }

    inner class OnChange: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            this@FormActivity.tvNota.text = progress.toString()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }
}