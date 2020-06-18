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
    lateinit var dataKeeper:TrainingDataKeeper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggessions)

        val sharedPreferences=this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        str= sharedPreferences.getString("Trainingset","").toString()
        val dataInString=sharedPreferences.getString("DataKeeper","").toString()
        dataKeeper= Gson().fromJson(dataInString,TrainingDataKeeper::class.java)
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
    fun wordPredictor(prefix:String){
        if(dataKeeper.dictFutureWord[prefix]?.size!=null)
            for(i in 0..dataKeeper.dictFutureWord[prefix]?.size!!-1){
                println(dataKeeper.dictFutureWord[prefix]?.toList()?.sortedBy { it.second }?.get(dataKeeper.dictFutureWord[prefix]?.size!!-i-1)?.first)
                println(dataKeeper.dictFutureWord[prefix]?.toList()?.sortedBy { it.second }?.get(dataKeeper.dictFutureWord[prefix]?.size!!-i-1)?.second)
                if(i>=2){
                    break
                }
            }
        if(dataKeeper.dictCompleteWord[prefix]?.size!=null)
            for(i in 0..dataKeeper.dictCompleteWord[prefix]?.size!!-1){
                println(dataKeeper.dictCompleteWord[prefix]?.toList()?.sortedBy { it.second }?.get(dataKeeper.dictCompleteWord[prefix]?.size!!-i-1)?.first)
                println(dataKeeper.dictCompleteWord[prefix]?.toList()?.sortedBy { it.second }?.get(dataKeeper.dictCompleteWord[prefix]?.size!!-i-1)?.second)
                if(i>=2){
                    break
                }
            }
    }
}