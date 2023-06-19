package com.example.pexeso2023

import android.os.SystemClock
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast

class PexesoGame(difficulty: Int):Game {

    companion object {
        const val TAG = "PexesoGame"
    }

    val startTime: Long
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
    var otoceneKarty=0
    lateinit var karty:List<Karta>

    init{
        startTime= SystemClock.elapsedRealtime()
        pocetKariet = difficulty
    }

    fun getTime() = SystemClock.elapsedRealtime()-startTime

    override val isWon: Boolean
        get() = uhadnutePary == (pocetKariet /2)


    override fun foundPair(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attempts(): Int {
        TODO("Not yet implemented")
    }

    override fun choosePair() {
        TODO("Not yet implemented")
    }

    override fun getObrazky(): List<Karta> {
        var obrazky = zoznamObrazkov.shuffled().take(pocetKariet/2)
        obrazky+=obrazky
        karty = obrazky.map { Karta(false,false, it) }
        karty=karty.shuffled()
        return karty
    }

    override fun otocKartu(position: Int) {
        var karta = karty[position]
        Log.d(TAG, "otacam kartu na pozicii $position")

        Log.d(TAG, "otacam kartu s obrazkom ${karta.obrazok}")

        when(otoceneKarty){
            0->{// otoc kartu a nacitaj jej obrazok
                Log.d(TAG, "otacam prvu kartu")
                otoceneKarty+=1
            }
            1->{//otoc kartu a porovnaj ci je to par
                Log.d(TAG, "otacam druhu kartu")
                otoceneKarty+=1
            }
            2->{// toast invalid move
                Log.d(TAG, "uz su 2 karty otocene")
            }
        }

    }

}