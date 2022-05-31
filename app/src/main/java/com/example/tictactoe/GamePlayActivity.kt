package com.example.tictactoe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.system.exitProcess

var playerTurn = true
class GamePlayActivity : AppCompatActivity() {
    lateinit var player1Tv : TextView
    lateinit var player2Tv : TextView

    lateinit var box1Btn : Button
    lateinit var box2Btn : Button
    lateinit var box3Btn : Button
    lateinit var box4Btn : Button
    lateinit var box5Btn : Button
    lateinit var box6Btn : Button
    lateinit var box7Btn : Button
    lateinit var box8Btn : Button
    lateinit var box9Btn : Button

    lateinit var resetBtn : Button

    //Variáveis do Jogo
    var player1count = 0
    var player2count = 0

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()

    var activeUser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)

        player1Tv = findViewById(R.id.idTvPlayer1)
        player2Tv = findViewById(R.id.idTvPlayer2)

        box1Btn = findViewById(R.id.idBtnBox1)
        box2Btn = findViewById(R.id.idBtnBox2)
        box3Btn = findViewById(R.id.idBtnBox3)
        box4Btn = findViewById(R.id.idBtnBox4)
        box5Btn = findViewById(R.id.idBtnBox5)
        box6Btn = findViewById(R.id.idBtnBox6)
        box7Btn = findViewById(R.id.idBtnBox7)
        box8Btn = findViewById(R.id.idBtnBox8)
        box9Btn = findViewById(R.id.idBtnBox9)

        resetBtn = findViewById(R.id.idBtnReset)
        resetBtn.setOnClickListener{ reset() }

        //tentando resolver o erro
        box1Btn.setOnClickListener{
            buttonClick(box1Btn)
        }
        box2Btn.setOnClickListener{
            buttonClick(box2Btn)
        }
        box3Btn.setOnClickListener{
            buttonClick(box3Btn)
        }
        box4Btn.setOnClickListener{
            buttonClick(box4Btn)
        }
        box5Btn.setOnClickListener{
            buttonClick(box5Btn)
        }
        box6Btn.setOnClickListener{
            buttonClick(box6Btn)
        }
        box7Btn.setOnClickListener{
            buttonClick(box7Btn)
        }
        box8Btn.setOnClickListener{
            buttonClick(box8Btn)
        }
        box9Btn.setOnClickListener{
            buttonClick(box9Btn)
        }
    }

    private fun buttonClick(view: View) {
        if(playerTurn) {
            val but = view as Button
            var cellID = 0
            when (but.id) {
                R.id.idBtnBox1 -> cellID = 1
                R.id.idBtnBox2 -> cellID = 2
                R.id.idBtnBox3 -> cellID = 3
                R.id.idBtnBox4 -> cellID = 4
                R.id.idBtnBox5 -> cellID = 5
                R.id.idBtnBox6 -> cellID = 6
                R.id.idBtnBox7 -> cellID = 7
                R.id.idBtnBox8 -> cellID = 8
                R.id.idBtnBox9 -> cellID = 9
            }
            playerTurn = false;
            Handler(Looper.getMainLooper()).postDelayed(Runnable { playerTurn = true } , 600)
            playNow(but, cellID)
            showCount()
        }
    }

    private fun playNow(buttonSelected: Button, currCell: Int) {
        if(activeUser == 1){
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1){
                Handler(Looper.getMainLooper()).postDelayed(Runnable { reset() }, 4000)
            } else if (singleUser){
                Handler(Looper.getMainLooper()).postDelayed(Runnable { robot() }, 500)
            } else {
                activeUser = 2
            }
        } else {
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#D2B804"))
            player2.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1){
                Handler(Looper.getMainLooper()).postDelayed(Runnable {reset()}, 4000)
            } else {
                activeUser = 1
            }
        }
    }

    private fun robot() {
        val rnd = (1..9).random()
        if(emptyCells.contains(rnd)){
           robot()
        } else {
            val btnSelected = when(rnd) {
                1 -> box1Btn
                2 -> box2Btn
                3 -> box3Btn
                4 -> box4Btn
                5 -> box5Btn
                6 -> box6Btn
                7 -> box7Btn
                8 -> box8Btn
                9 -> box9Btn
                else -> {
                    box1Btn
                }
            }
            emptyCells.add(rnd)
            btnSelected.text = "O"
            btnSelected.setTextColor(Color.parseColor("#D2B804"))
            player2.add(rnd)
            btnSelected.isEnabled = false
            val checkWinner = checkWinner()
            if(checkWinner == 1){
                Handler(Looper.getMainLooper()).postDelayed(Runnable { reset() }, 2000)
            }
        }
    }

    private fun checkWinner(): Int {
        if(
            (player1.contains(1) && player1.contains(2) && player1.contains(3)) || //linha 1
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) || //linha 2
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) || //linha 3

            (player1.contains(1) && player1.contains(4) && player1.contains(7)) || //coluna 1
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) || //coluna 2
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) || //colune 3

            (player1.contains(1) && player1.contains(5) && player1.contains(9)) || //diagonal 1
            (player1.contains(3) && player1.contains(5) && player1.contains(7))    //diagonal 2
        ) {
            player1count++
            btnDisable()
            disableReset()
            gameOverMensage("Player 1 Wins")
            return 1
        } else if(
            (player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||

            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||

            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7))
        ) {
            player2count++
            btnDisable()
            disableReset()
            gameOverMensage("Player 2 Wins")
            return 1
        } else if(
        // deu velha
            emptyCells.contains(1) &&
            emptyCells.contains(2) &&
            emptyCells.contains(3) &&
            emptyCells.contains(4) &&
            emptyCells.contains(5) &&
            emptyCells.contains(6) &&
            emptyCells.contains(7) &&
            emptyCells.contains(8) &&
            emptyCells.contains(9)
        ){
            gameOverMensage("Game Draw")
            return 1
        } else{
            return 0
        }
    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for(i in 1..9){
            var btnSelected : Button?
            btnSelected = when(i){
                1 -> box1Btn
                2 -> box2Btn
                3 -> box3Btn
                4 -> box4Btn
                5 -> box5Btn
                6 -> box6Btn
                7 -> box7Btn
                8 -> box8Btn
                9 -> box9Btn
                else -> {
                    box1Btn
                }
            }

            btnSelected.isEnabled = true
            btnSelected.text = ""
        }
    }

    private fun disableReset(){
        resetBtn.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed(Runnable { resetBtn.isEnabled = true } , 2200)
    }

    private fun btnDisable() {
        for(i in 1..9){
            var btnSelected : Button?
            btnSelected = when(i){
                1 -> box1Btn
                2 -> box2Btn
                3 -> box3Btn
                4 -> box4Btn
                5 -> box5Btn
                6 -> box6Btn
                7 -> box7Btn
                8 -> box8Btn
                9 -> box9Btn
                else -> {
                    box1Btn
                }
            }
            btnSelected.isEnabled = true
        }
    }

    private fun gameOverMensage(msg : String){
        val build = AlertDialog.Builder(this)
        build.setTitle("Game Over")
        build.setMessage("$msg \n\n" + "Do you want to play again?")
        build.setPositiveButton("Ok"){dialog,  witch ->
            reset()
        }
        build.setNegativeButton("Exit"){dialog, witch ->
            exitProcess(1)
        }
        Handler(Looper.getMainLooper()).postDelayed(Runnable { build.show() }, 200)
    }

    private fun showCount(){
        player1Tv.text = "Player 1: $player1count"
        player2Tv.text = "Player 2: $player2count"
    }

}

