package com.thelazypeople.markov_chain_visualiser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var str=""
    var f=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show.movementMethod= ScrollingMovementMethod()
        show.computeScroll()


        val sharedPreferences=this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        val prefEditor=sharedPreferences.edit()

        start.setOnClickListener {

            str=train.text.toString()
            display.text="PREFIX"
            display2.text="SUFFIX"
            show2.movementMethod= ScrollingMovementMethod()
            markov(1, 2)
            f=1
        }

        test.setOnClickListener {
            if(f==0)
            {
                Toast.makeText(this,"Firstly Train the model", Toast.LENGTH_LONG).show()
            }
            else {
                prefEditor.putString("Trainingset", str)
                prefEditor.apply()
                prefEditor.commit()
                val intent = Intent(this, Suggessions::class.java)
                startActivity(intent)
            }
        }
    }
    fun markov(keySize: Int, outputSize: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            //require(keySize >= 1) { "Key size can't be less than 1" }
            // val str="jump? asked the Scarecrow. Never. He sits day after day in the great fields. They kept on walking, however, and at night the moon came out and shone brightly. So they lay down among the sweet smelling yellow flowers and slept soundly until morning. When it was daylight, the girl bathed her face in her hands, and she set to work in one of the shelves as she passed; it was labelled 'ORANGE MARMALADE', but to her great delight it fitted! Alice opened the door and closer to one another, for the stillness of the empty room was more dreadful"

            val words = str.trimEnd().split(' ')

            //print(words)
            require(outputSize in keySize..words.size) { "Output size is out of range ${outputSize}" }

            val dict = mutableMapOf<String, MutableList<String>>()
            var prefix = ""
            var suffix = ""

            for (i in 0..(words.size - keySize)) {

                prefix += (words.subList(i, i + keySize).joinToString(" ")) + "\n"

                show.text = prefix

                delay(400)
                var scrollAmount = show.getLayout().getLineTop(show.getLineCount()) - show.getHeight();
                // if there is no need to scroll, scrollAmount will be <=0
                if (scrollAmount > 6)
                    show.scrollTo(0, scrollAmount);
                else
                    show.scrollTo(0, 0);

                suffix += if (i + keySize < words.size) words[i + keySize] else ""
                suffix += "\n"
                show2.text = suffix
                delay(400)
                var scrollAmount2 = show.getLayout().getLineTop(show2.getLineCount()) - show2.getHeight();
                // if there is no need to scroll, scrollAmount will be <=0
                if (scrollAmount2 > 6)
                    show2.scrollTo(0, scrollAmount2);
                else
                    show2.scrollTo(0, 0);
                delay(200)

                val suffixes = dict.getOrPut(prefix) { mutableListOf() }
                suffixes += suffix
            }

        }
    }
}