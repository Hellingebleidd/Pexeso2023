package com.example.pexeso2023


import android.os.Bundle

import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PexesoActivity : AppCompatActivity() {

    companion object{
        const val TAG= "Pexeso Activity"
    }

    private lateinit var hraciaPlocha : RecyclerView
    private var karty = 0
    private lateinit var plocha: Plocha
    private lateinit var adapter: PexesoAdapter
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

        hraciaPlocha = findViewById(R.id.hraciaPlocha)

        val intent = intent
        karty = intent.getIntExtra("difficulty", 0)
        Log.d("HRA", "pocet parov kariet: $karty")

        plocha = Plocha(karty)

        game=PexesoGame(karty)
        adapter = PexesoAdapter(this, plocha, game.getObrazky(), object: KartaClickListener{
            override fun onKartaClick(position:Int, kartaButton: ImageButton) {
                Log.d(TAG, "poloha karty: ${position}")
                updateBoard(position, kartaButton)
            }
        })

        hraciaPlocha.adapter=adapter
        hraciaPlocha.setHasFixedSize(true)
        hraciaPlocha.layoutManager = GridLayoutManager(this, plocha.getStlpce()) //zatial hardcoded potom podla obtiaznosti


    }

    private fun updateBoard(position:Int, kartaButton: ImageButton) {

        if(game.otoceneKarty<2){
            game.otocKartu(position, kartaButton)
        }
        }

    }


