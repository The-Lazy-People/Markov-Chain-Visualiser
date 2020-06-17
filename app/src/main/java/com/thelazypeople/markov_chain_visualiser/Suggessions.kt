package com.thelazypeople.markov_chain_visualiser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_suggessions.*

class Suggessions : AppCompatActivity() {

    var pre=""
    var str=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggessions)

        val sharedPreferences=this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        str= sharedPreferences.getString("Trainingset","").toString()
        val dataInString=sharedPreferences.getString("DataKeeper","").toString()
        val dataKeeper= Gson().fromJson(dataInString,TrainingDataKeeper::class.java)
        prefix.setOnClickListener {
            pre=prefix.text.toString()
            suggestions.text=pre
            // second part of function
        }

        back.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}