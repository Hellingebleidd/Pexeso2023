package com.vma.pexeso2023

data class Plocha (val pocetKariet: Int, val portrait: Boolean) {
    fun getStlpce():Int {
            return when(pocetKariet){
                EASY_GAME_CARDS -> {
                    if (portrait) 2 else (EASY_GAME_CARDS / 2)
                }
                MEDIUM_GAME_CARDS -> {
                    if (portrait) 3 else (MEDIUM_GAME_CARDS / 3)
                }
                HARD_GAME_CARDS -> {
                    if (portrait) 3 else (HARD_GAME_CARDS / 3)
                }
                else -> {2}
            }
        }

    val riadky = pocetKariet / getStlpce()
}