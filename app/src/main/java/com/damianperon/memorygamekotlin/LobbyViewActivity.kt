package com.damianperon.memorygamekotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class LobbyViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, GamePlayViewActivity::class.java)

        button3x4.setOnClickListener {
            intent.putExtra("totalCards", 12)
            intent.putExtra("columns", 3)
            startActivity(intent)
        }
        button4x4.setOnClickListener {
            intent.putExtra("totalCards", 16)
            intent.putExtra("columns", 4)

            startActivity(intent)
        }
        button4x5.setOnClickListener {
            intent.putExtra("totalCards", 20)
            intent.putExtra("columns", 4)
            startActivity(intent)
        }
        button5x2.setOnClickListener {
            intent.putExtra("totalCards", 10)
            intent.putExtra("columns", 5)
            startActivity(intent)
        }


    }
}
