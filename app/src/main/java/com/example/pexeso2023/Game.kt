package com.example.pexeso2023

import android.widget.ImageButton
import java.io.Serializable

const val EASY_GAME_CARDS = 8
const val MEDIUM_GAME_CARDS = 12
const val HARD_GAME_CARDS = 18 //20 //16

interface Game: Serializable {

    val isWon: Boolean
    val otoceneKarty: Int
    var foundPair: Boolean
    fun attempts():Int
    fun choosePair(position1:Int, button1:ImageButton, position2:Int, button2: ImageButton)
    fun getObrazky():List<Karta>
    fun otocKartu(position: Int, button: ImageButton)
}