package com.example.pexeso2023

import java.io.Serializable

const val EASY_GAME_CARDS = 8
const val MEDIUM_GAME_CARDS = 12
const val HARD_GAME_CARDS = 20 //16

interface Game: Serializable {

    val isWon: Boolean
    fun foundPair(): Boolean
    fun attempts():Int
    fun choosePair()
//    fun setBoardSize(pocetKariet: Int)
    fun getObrazky():List<Int>
}