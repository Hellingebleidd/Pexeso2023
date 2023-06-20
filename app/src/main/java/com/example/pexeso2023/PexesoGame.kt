package com.example.pexeso2023

import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.nfc.Tag
import android.os.SystemClock
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlin.math.max

class PexesoGame(difficulty: Int, banner: CardView):Game {

    companion object {
        const val TAG = "PexesoGame"
        const val RYCHLOST: Long = 500
    }

    private val startTime: Long
    private var uhadnutePary =0
    private val pocetKariet: Int
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
    var pocetOtocenych=0
    private val scoreBanner: CardView
    lateinit var karty:List<Karta>
    var bestScore=0

    init{
        startTime= SystemClock.elapsedRealtime()
        pocetKariet = difficulty
        scoreBanner=banner
    }

    val getTime
     get() = SystemClock.elapsedRealtime()-startTime

    override val isWon: Boolean
        get() = (uhadnutePary == (pocetKariet / 2))

    override val otoceneKarty:Int
        get() = pocetOtocenych

    override var foundPair:Boolean = false

    override fun attempts(): Int {
        TODO("Not yet implemented")
    }

    override fun choosePair(
        position1: Int,
        button1: ImageButton,
        position2: Int,
        button2: ImageButton
    ) {
        var karta1 = karty[position1]
        var karta2 = karty[position2]
        Log.d(TAG, "prva karta obr: ${karta1.obrazok}")
        Log.d(TAG, "druha karta obr: ${karta2.obrazok}")
        if(karta1.obrazok==karta2.obrazok){
            foundPair=true
            karta1.maPar=true
            karta2.maPar=true
            //disabluju sa
            button1.isEnabled=false
            button1.isVisible=false
//            button1.colorFilter = LightingColorFilter(Color.GRAY, Color.BLACK)
            button2.isEnabled=false
            button2.isVisible=false
//            button2.colorFilter = LightingColorFilter(Color.GRAY, Color.BLACK)
            uhadnutePary+=1
        }else{
            //sa otocia nazad
            button1.isEnabled=true
            otocKartu(position1,button1)
            otocKartu(position2,button2)

        }
        Log.d(TAG, "found pair: $foundPair")
        Log.d(TAG,"uhadnute pary: $uhadnutePary / ${pocetKariet/2}")
        pocetOtocenych=0

        if(isWon){
            Log.i(TAG, "si vyhral")
            var score = (15*60*1000-getTime)/1000 * pocetKariet/15 //15min - cas hry na sekundy v zavislosti od obtiaznosti
            var bestScoreText: TextView = scoreBanner.findViewById(R.id.tv_BestScore)
            var yourScoreText: TextView = scoreBanner.findViewById(R.id.tv_YourScore)
            var yourTime: TextView = scoreBanner.findViewById(R.id.tvYourTime)
            val min = getTime / 1000 / 60
            val sec = getTime / 1000 % 60
            bestScore = (if(bestScore>score) bestScore else score.toInt())
            bestScoreText.setText("Best Score: $bestScore")
            yourScoreText.setText("Score: $score")
            yourTime.setText("Time: $min:$sec")
            scoreBanner.isVisible=true

        }else{
            Log.i(TAG, "nic")
        }
//        return foundPair
    }

    override fun getObrazky(): List<Karta> {
        var obrazky = zoznamObrazkov.shuffled().take(pocetKariet/2)
        obrazky+=obrazky
        karty = obrazky.map { Karta(false,false, it) }
        karty=karty.shuffled()
        return karty
    }

    override fun otocKartu(position: Int, button: ImageButton) {
        var karta = karty[position]
        Log.d(TAG, "otacam kartu na pozicii $position")
        Log.d(TAG, "otacam kartu s obrazkom ${karta.obrazok}")

        pocetOtocenych+=1

        Log.d(TAG, "otocene karty: $pocetOtocenych")

        if(karta.vidnoObrazok){
            button.animate().apply {
                duration= RYCHLOST
                rotationYBy(-180f)
            }.start()
            button.postDelayed({button.setImageResource(android.R.color.holo_green_light)}, RYCHLOST /2)
            karta.vidnoObrazok=false
        }else{
            button.animate().apply {
                duration= RYCHLOST
                rotationYBy(180f)
            }.start()
            button.postDelayed({button.setImageResource(karta.obrazok)}, RYCHLOST /2)
            karta.vidnoObrazok=true
        }


    }

}

