package com.example.pexeso2023


import android.graphics.Color
import android.graphics.LightingColorFilter
import android.os.Bundle

import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
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
    var positionPrvejKarty = 0
    var positionDruhejKarty = 0
    private lateinit var button1:ImageButton
    private lateinit var button2:ImageButton

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
//        game.otocKartu(position, kartaButton)

        when(game.otoceneKarty){
            0->{
                game.otocKartu(position, kartaButton)
                positionPrvejKarty=position
                //zapamataj button
                button1=kartaButton
            }
            1->{
                game.otocKartu(position, kartaButton)
                positionDruhejKarty = position
                button2=kartaButton
                var najdenyPar = game.choosePair(positionPrvejKarty, positionDruhejKarty) //toto vrati bool
                if(najdenyPar) {
                    // true disabluj buttony
                    button1.isEnabled=false
                    button1.colorFilter = LightingColorFilter(Color.GRAY, Color.BLACK)
                    button2.isEnabled=false
                    button2.colorFilter = LightingColorFilter(Color.GRAY, Color.BLACK)
                }else {
                    //false otoc nazad
//                    Thread.sleep(1000)
                    game.otocKartu(positionPrvejKarty,button1)
                    game.otocKartu(positionDruhejKarty,button2)
                }
            }
            2->{
                Toast.makeText(this, "Invalid move", Toast.LENGTH_LONG).show()
            }
        }
        Log.d(TAG, "otocene karty: ${game.otoceneKarty}")
        }

    }


