package com.example.pexeso2023

import android.os.SystemClock

class PexesoGame(difficulty: Int):Game {

    val startTime: Long

    init{
        startTime= SystemClock.elapsedRealtime()
    }

    fun getTime() = SystemClock.elapsedRealtime()-startTime

    override fun isWon(): Boolean {
        TODO("Not yet implemented")
    }

    override fun foundPair(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attempts(): Int {
        TODO("Not yet implemented")
    }

    override fun choosePair() {
        TODO("Not yet implemented")
    }
}