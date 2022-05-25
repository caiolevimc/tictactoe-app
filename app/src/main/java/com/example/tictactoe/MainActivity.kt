package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

var singleUser = false
class MainActivity : AppCompatActivity() {

    lateinit var singlePlayerBtn: Button
    lateinit var multiPlayerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singlePlayerBtn = findViewById(R.id.idBtnSinglePlayer)
        multiPlayerBtn = findViewById(R.id.idBtnMultiPlayer)

        val intent = Intent(this, GamePlayActivity::class.java)

        singlePlayerBtn.setOnClickListener{
            singleUser = true
            startActivity(intent)
        }

        multiPlayerBtn.setOnClickListener{
            singleUser = false

            startActivity(intent)
        }

    }
}