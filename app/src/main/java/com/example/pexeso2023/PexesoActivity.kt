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
    private val zoznamObrazkov = listOf(
        R.drawable.hauko,
        R.drawable.kohutik,
        R.drawable.konik,
        R.drawable.krtek,
        R.drawable.kvietok,
        R.drawable.liska,
        R.drawable.motylik,
        R.drawable.myska,
        R.drawable.pavuk,
        R.drawable.slimak,
        R.drawable.sova,
        R.drawable.vtacik,
        R.drawable.zabka,
        R.drawable.zajcik,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

        hraciaPlocha = findViewById(R.id.hraciaPlocha)

        val intent = intent
        karty = intent.getIntExtra("difficulty", 0)
        Log.i("HRA", "pocet parov kariet: $karty")

        plocha = Plocha(karty)

        var obrazky = zoznamObrazkov.shuffled().take(plocha.pocetKariet/2)
        obrazky+=obrazky
        obrazky=obrazky.shuffled()

        val adapter = PexesoAdapter(this, plocha, obrazky)

        hraciaPlocha.adapter=adapter
        hraciaPlocha.setHasFixedSize(true)
        hraciaPlocha.layoutManager = GridLayoutManager(this, plocha.getStlpce()) //zatial hardcoded potom podla obtiaznosti
    }
}