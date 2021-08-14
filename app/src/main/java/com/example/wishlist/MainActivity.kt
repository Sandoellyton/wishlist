package com.example.wishlist

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listDesejos: ListView
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var list: ArrayList<Desejo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.list = arrayListOf()

        this.listDesejos = findViewById(R.id.listDesejos)
        this.btnAdd = findViewById(R.id.btnAdd)

        this.listDesejos.adapter = ArrayAdapter<Desejo>(this, android.R.layout.simple_list_item_1, this.list)

        val resultForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val desejo = it.data?.getSerializableExtra("DESEJO") as Desejo
                (this.listDesejos.adapter as ArrayAdapter<Desejo>).add(desejo)
            }
        }

        this.btnAdd.setOnClickListener{
            val intent = Intent(this, FormActivity::class.java)
            resultForm.launch(intent)
        }

        this.listDesejos.setOnItemClickListener(OnItemClick())
        this.listDesejos.setOnItemLongClickListener(OnItemLongClick())
    }

    inner class OnItemClick: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val desejo = this@MainActivity.list[position]
            Toast.makeText(this@MainActivity, desejo.descricao, Toast.LENGTH_SHORT).show()
        }
    }

    inner class OnItemLongClick: AdapterView.OnItemLongClickListener{
        override fun onItemLongClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ): Boolean {
            //this@MainActivity.lista.removeAt(position)
            apagarItemLista(this@MainActivity, position)
            (this@MainActivity.listDesejos.adapter as ArrayAdapter<Desejo>).notifyDataSetChanged()
            return true
        }
    }

    fun apagarItemLista(activity: Activity, position: Int) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Deletar item")
        alertDialog.setMessage("Quer mesmo deletar o item?")

        alertDialog.setPositiveButton("Sim", { _, _ ->
            this@MainActivity.list.removeAt(position)
            (this@MainActivity.listDesejos.adapter as ArrayAdapter<Desejo>).notifyDataSetChanged()
            Toast.makeText(this, "Item apagado com sucesso!", Toast.LENGTH_LONG).show()
        })

        alertDialog.setNegativeButton("Não", { _, _ ->
            Toast.makeText(this, "Item deletado!", Toast.LENGTH_LONG).show()

        })
        alertDialog.show()
    }
}