package com.example.pexeso2023.databaza

import androidx.lifecycle.LiveData

class ScoreRepo(private val scoreDao: ScoreDao) {

    val getAll: LiveData<List<Score>> = scoreDao.getAll() //mozno zmenit na flow

    fun upsertScore(score:Score){
        scoreDao.upsertScore(score)
    }
}