package com.example.pexeso2023

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PexesoActivity : AppCompatActivity() {

    private lateinit var hraciaPlocha : RecyclerView
    private var karty = 0
//    private var sirkaPlochy=0
    private lateinit var plocha: Plocha
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

        hraciaPlocha = findViewById(R.id.hraciaPlocha)

        val intent = intent
        karty = intent.getIntExtra("difficulty", 0)
        Log.i("HRA", "pocet parov kariet: $karty")

        plocha = Plocha(karty)

        game=PexesoGame(karty)
        val adapter = PexesoAdapter(this, plocha, game.getObrazky())

        hraciaPlocha.adapter=adapter
        hraciaPlocha.setHasFixedSize(true)
        hraciaPlocha.layoutManager = GridLayoutManager(this, plocha.getStlpce()) //zatial hardcoded potom podla obtiaznosti


    }


}