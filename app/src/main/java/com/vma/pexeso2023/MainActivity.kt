package com.vma.pexeso2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.util.Log
import com.example.pexeso2023.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "Intro Screen"
    }

    private lateinit var btnEasy: Button
    private lateinit var btnMedium: Button
    private lateinit var btnHard: Button

    private lateinit var btnLeaderboard: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        btnLeaderboard=findViewById(R.id.floatingActionButton)

        btnEasy=findViewById(R.id.buttonEasy)
        btnMedium=findViewById(R.id.buttonMedium)
        btnHard=findViewById(R.id.buttonHard)


        val intent = Intent(this, PexesoActivity::class.java)

        btnEasy.setOnClickListener {
            intent.putExtra("difficulty", EASY_GAME_CARDS)  //ked chcem poslat nejake parametre napr obtiaznost
            Log.i(TAG, "zvolena Easy obtiaznost, karty: $EASY_GAME_CARDS")
            startActivity(intent)
        }
        btnMedium.setOnClickListener {
            Log.i(TAG, "zvolena Medium obtiaznost")
            intent.putExtra("difficulty", MEDIUM_GAME_CARDS)
            startActivity(intent)
        }
        btnHard.setOnClickListener {
            Log.i(TAG, "zvolena Hard obtiaznost")
            intent.putExtra("difficulty", HARD_GAME_CARDS)
            startActivity(intent)
        }

        val intentScore = Intent(this, LeaderBoardActivity::class.java)
        btnLeaderboard.setOnClickListener {
            startActivity(intentScore)
        }
    }
}