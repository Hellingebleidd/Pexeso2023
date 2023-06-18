package com.example.pexeso2023

data class Plocha (val pocetKariet: Int) {
    fun getStlpce():Int {
            return when(pocetKariet){
                EASY_GAME_CARDS -> 2
                MEDIUM_GAME_CARDS ->3
                HARD_GAME_CARDS -> 4
                else -> {2}
            }
        }

    val riadky = pocetKariet / getStlpce()
}