package com.example.pexeso2023.databaza

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("SELECT * FROM score_table ORDER BY cas DESC")
    fun getAll(): Flow<List<Score>>

    @Upsert
    fun upsertScore(score: Score)

//    @Query("DELETE FROM score_table WHERE datum < (NOW() - 30) ")
//    fun deleteScore(datum:String)

//    fun orderByDate()
}