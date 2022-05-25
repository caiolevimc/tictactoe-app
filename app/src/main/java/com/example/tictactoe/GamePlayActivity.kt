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
        resetBtn.setOnClickListener{
            reset()
        }

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
                Handler(Looper.getMainLooper()).postDelayed(Runnable { reset() }, 2000)
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
            var btnSelected = when(rnd) {
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
            var checkWinner = checkWinner()
            if(checkWinner == 1){
                Handler(Looper.getMainLooper()).postDelayed(Runnable { reset() }, 2000)
            }
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
//tentativa site
/*
    lateinit var button : Button
    lateinit var button2 : Button
    lateinit var button3 : Button
    lateinit var button4 : Button
    lateinit var button5 : Button
    lateinit var button6 : Button
    lateinit var button7 : Button
    lateinit var button8 : Button
    lateinit var button9 : Button
    lateinit var button10 : Button
    lateinit var textView : TextView
    lateinit var textView2 : TextView

    //tentativa site
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.button)
        button2 = findViewById<Button>(R.id.button2)
        button3 = findViewById<Button>(R.id.button3)
        button4 = findViewById<Button>(R.id.button4)
        button5 = findViewById<Button>(R.id.button5)
        button6 = findViewById<Button>(R.id.button6)
        button7 = findViewById<Button>(R.id.button7)
        button8 = findViewById<Button>(R.id.button8)
        button9 = findViewById<Button>(R.id.button9)
        button10 = findViewById<Button>(R.id.button10)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)

        // reset button listener
        button10.setOnClickListener {
            reset()

        }
    }

    // player winning count
    var player1Count = 0
    var player2Count = 0

    // this function handle the click event on the board.
    fun clickfun(view: View) {
        if (playerTurn) {
            val but = view as Button
            var cellID = 0
            when (but.id) {
                R.id.button -> cellID = 1
                R.id.button2 -> cellID = 2
                R.id.button3 -> cellID = 3
                R.id.button4 -> cellID = 4
                R.id.button5 -> cellID = 5
                R.id.button6 -> cellID = 6
                R.id.button7 -> cellID = 7
                R.id.button8 -> cellID = 8
                R.id.button9 -> cellID = 9
            }
            playerTurn = false;
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playnow(but, cellID)
        }
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1

    // this function update update the game board after every move.
    fun playnow(
        buttonSelected: Button,
        currCell: Int
    ) {   //val audio = MediaPlayer.create(this , R.raw.poutch)
        if (activeUser == 1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currCell)
            emptyCells.add(currCell)
            //audio.start()
            buttonSelected.isEnabled = false
            //Handler().postDelayed(Runnable { audio.release() } , 200)
            val checkWinner = checkwinner()
            if (checkWinner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            } else if (singleUser) {
                Handler().postDelayed(Runnable { robot() }, 500)
            } else
                activeUser = 2
        } else {
            buttonSelected.text = "O"
            //audio.start()
            buttonSelected.setTextColor(Color.parseColor("#D22BB804"))
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)
            //Handler().postDelayed(Runnable { audio.release() } , 200)
            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
            if (checkWinner == 1)
                Handler().postDelayed(Runnable { reset() }, 4000)
        }
    }

    // this function resets the game.
    fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1;
        for (i in 1..9) {
            var buttonselected: Button?
            buttonselected = when (i) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button
                }
            }
            buttonselected.isEnabled = true
            buttonselected.text = ""
            textView.text = "Player1 : $player1Count"
            textView2.text = "Player2 : $player2Count"
        }
    }

    // this function disable all the button on the board for a while.
    fun disableReset() {
        button10.isEnabled = false
        Handler().postDelayed(Runnable { button10.isEnabled = true }, 2200)
    }

    fun robot() {
        val rnd = (1..9).random()
        if (emptyCells.contains(rnd))
            robot()
        else {
            val buttonselected: Button?
            buttonselected = when (rnd) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button
                }
            }
            emptyCells.add(rnd);
            // move audio
            //val audio = MediaPlayer.create(this , R.raw.poutch)
            //audio.start()
            //Handler().postDelayed(Runnable { audio.release() } , 500)
            buttonselected.text = "O"
            buttonselected.setTextColor(Color.parseColor("#D22BB804"))
            player2.add(rnd)
            buttonselected.isEnabled = false
            var checkWinner = checkwinner()
            if (checkWinner == 1)
                Handler().postDelayed(Runnable { reset() }, 2000)
        }
    }

    private fun checkwinner() : Int{
        if(
            (player1.contains(1) && player1.contains(1) && player1.contains(1)) || //linha 1
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) || //linha 2
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) || //linha 3

            (player1.contains(1) && player1.contains(4) && player1.contains(7)) || //coluna 1
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) || //coluna 2
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) || //colune 3

            (player1.contains(1) && player1.contains(5) && player1.contains(9)) || //diagonal 1
            (player1.contains(3) && player1.contains(5) && player1.contains(7))    //diagonal 2
        ) {
            player1Count++
            reset()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 Wins \n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok"){dialog,  witch ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, witch ->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        } else if(
            (player2.contains(1) && player2.contains(1) && player2.contains(1)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||

            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||

            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7))
        ) {
            player2Count++
            reset()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 Wins \n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok"){dialog,  witch ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, witch ->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
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
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Game Draw \n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok"){dialog,  witch ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, witch ->
                exitProcess(1)
            }
            build.show()
            return 1
        } else{
            return 0
        }
    }
}
*/


//tentativa vídeo
/*
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
        resetBtn.setOnClickListener{
            reset()
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
            player1Tv.text = "Player 1: $player1count"
            player2Tv.text = "Player 2: $player2count"
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
            Handler().postDelayed(Runnable { playerTurn = true } , 600)
            playnow(but, cellID)
        }
    }

    private fun playnow(buttonSelected: Button, currCell: Int) {
        if(activeUser == 1){
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkWinner()
            if (checkWinner == 1){
                Handler().postDelayed(Runnable { reset() }, 2000)
            } else if (singleUser){
                Handler().postDelayed(Runnable { robot() }, 500)
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
                Handler().postDelayed(Runnable {reset()}, 4000)
            }
        }
    }

    private fun robot() {
        val rnd = (1..9).random()
        if(emptyCells.contains(rnd)){
           robot()
        } else {
            var btnSelected = when(rnd) {
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
            var checkWinner = checkWinner()
            if(checkWinner == 1){
                Handler().postDelayed(Runnable { reset() }, 2000)
            }
        }
    }

    private fun disableReset(){
        resetBtn.isEnabled = false
        Handler().postDelayed(Runnable { resetBtn.isEnabled = true } , 2200)
    }

    private fun btnDisable() {
        reset()
    }

    private fun checkWinner(): Int {
        if(
            (player1.contains(1) && player1.contains(1) && player1.contains(1)) || //linha 1
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
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 Wins \n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok"){dialog,  witch ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, witch ->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        } else if(
            (player2.contains(1) && player2.contains(1) && player2.contains(1)) ||
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
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 Wins \n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok"){dialog,  witch ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, witch ->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
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
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Game Draw \n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok"){dialog,  witch ->
                reset()
            }
            build.setNegativeButton("Exit"){dialog, witch ->
                exitProcess(1)
            }
            build.show()
            return 1
        } else{
            return 0
        }
    }
*/



