package com.example.pexeso2023.databaza

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ScoreRepo(private val scoreDao: ScoreDao) {

//    val getAll: LiveData<List<Score>> = scoreDao.getAll() //mozno zmenit na flow
    val score = scoreDao.getAll()

    @WorkerThread
    fun upsertScore(score:Score){
        scoreDao.upsertScore(score)
    }
}