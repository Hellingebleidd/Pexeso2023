package com.example.pexeso2023

import java.io.Serializable

const val EASY_GAME_CARDS = 8
const val MEDIUM_GAME_CARDS = 12
const val HARD_GAME_CARDS = 16

interface Game: Serializable {

    fun isWon(): Boolean
    fun foundPair(): Boolean
    fun attempts():Int
    fun choosePair()
}