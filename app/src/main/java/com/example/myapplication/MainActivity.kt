package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
        private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appTitle: TextView = findViewById(R.id.title)
        var textResult: TextView = findViewById(R.id.text_view)
        var editText: EditText = findViewById(R.id.numbers)
        var buttom: Button = findViewById(R.id.button)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result: String? = prefs.getString("result", null)

        if(result != null){
            textResult.text = "Última aposta: $result"
        }

        appTitle.setText(R.string.app_title)
        buttom.setText((R.string.save))

        buttom.setOnClickListener {
            val text = editText.text.toString()
            handleSortNumber(text, textResult)
        }
    }

   private fun handleSortNumber(text: String, textResult: TextView){
        if(text.isEmpty()){
            Toast.makeText(this,"Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }
       val qtd = text.toInt()
       if(qtd < 6 || qtd > 15){
           Toast.makeText(this,"Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
           return
       }

       val numbers = mutableSetOf<Int>()
       val random = Random()

       while (true){
           val number = random.nextInt(60)
           numbers.add(number + 1)

           if (numbers.size == qtd){
               break
           }
       }
       textResult.text = numbers.joinToString(" - ")
       val editor = prefs.edit()
       editor.putString("result", textResult.text.toString())
       editor.apply()
    }
}