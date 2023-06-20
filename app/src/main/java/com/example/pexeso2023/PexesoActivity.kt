package com.example.pexeso2023


import android.os.Bundle
import android.os.Handler
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
    var portrait = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

        hraciaPlocha = findViewById(R.id.hraciaPlocha)

        val intent = intent
        karty = intent.getIntExtra("difficulty", 0)
        Log.d("HRA", "pocet parov kariet: $karty")

        when(resources.configuration.orientation){
            1->{//portrait
                portrait = true
//                stlpce = plocha.getStlpce()
//                Log.d(TAG, "sirka portrait ${plocha.getStlpce()}")
            }
            2->{//landscape
                portrait = false
//                stlpce = plocha.riadky
//                Log.d(TAG, "sirka sirka landscape ${plocha.riadky}")

            }}

            plocha = Plocha(karty, portrait)
            game=PexesoGame(karty, this)


//        val orientation=resources.configuration.orientation
        var stlpce = plocha.getStlpce()
        if (portrait) stlpce=plocha.getStlpce() else plocha.riadky
//        if(portrait) stlpce = plocha.


        adapter = PexesoAdapter(this, plocha, game.getObrazky(), object: KartaClickListener{
            override fun onKartaClick(position:Int, kartaButton: ImageButton) {
                Log.d(TAG, "poloha karty: $position")
                updateBoard(position, kartaButton)
            }
        })

        hraciaPlocha.adapter=adapter
        hraciaPlocha.setHasFixedSize(true)


        hraciaPlocha.layoutManager = GridLayoutManager(this, stlpce)

    }

    private fun updateBoard(position:Int, kartaButton: ImageButton) {
        when(game.otoceneKarty){
            0->{
                game.otocKartu(position, kartaButton)
                positionPrvejKarty=position
                button1=kartaButton
                button1.isEnabled=false
            }
            1->{
                game.otocKartu(position, kartaButton)
                positionDruhejKarty = position
                button2=kartaButton

                Handler().postDelayed({
                    game.choosePair(positionPrvejKarty, button1 ,positionDruhejKarty, button2)
                },700)
            }
            2->{
                Toast.makeText(this, "Invalid move", Toast.LENGTH_LONG).show()
            }
        }
        Log.d(TAG, "otocene karty: ${game.otoceneKarty}")

    }

}


