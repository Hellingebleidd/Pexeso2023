package com.example.pexeso2023

import android.os.SystemClock

class PexesoGame(difficulty: Int):Game {

    val startTime: Long
    private var uhadnutePary =0
    private val pocetKariet: Int

    init{
        startTime= SystemClock.elapsedRealtime()
        pocetKariet = difficulty

    }

    fun getTime() = SystemClock.elapsedRealtime()-startTime

    override val isWon: Boolean
        get() = uhadnutePary == pocetKariet /2


    override fun foundPair(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attempts(): Int {
        TODO("Not yet implemented")
    }

    override fun choosePair() {
        TODO("Not yet implemented")
    }

//    override fun setBoardSize(pocetKariet: Int) {
//        var columns=0
//        when(pocetKariet){
//            EASY_GAME_CARDS -> columns=2
//            MEDIUM_GAME_CARDS-> columns=3
//            HARD_GAME_CARDS -> columns=4
//        }
//    }
}