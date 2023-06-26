package com.vma.pexeso2023

import android.content.Context
import android.os.SystemClock
import com.example.pexeso2023.R


class PexesoGame(difficulty: Int, private val context: Context):Game {

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
    lateinit var karty:List<Karta>
    var bestTime: Long=0

    init{
        startTime= SystemClock.elapsedRealtime()
        pocetKariet = difficulty
    }

    val getTime
     get() = SystemClock.elapsedRealtime()-startTime

    override val isWon: Boolean
        get() = (uhadnutePary == (pocetKariet / 2))

    override val otoceneKarty:Int
        get() = pocetOtocenych

    override var foundPair:Boolean = false


    fun konvertujCas(ms: Long):String{
        val min = ms / 1000 / 60
        val sec = ms / 1000 % 60
        return "$min:$sec"
    }

    override fun getObrazky(): List<Karta> {
        var obrazky = zoznamObrazkov.shuffled().take(pocetKariet/2)
        obrazky+=obrazky
        karty = obrazky.map { Karta(false,false, it) }
        karty=karty.shuffled()
        return karty
    }



}

