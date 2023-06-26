package com.vma.pexeso2023.databaza

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ScoreDao {

    @Query("SELECT * FROM score_table ORDER BY id DESC")
    suspend fun getAll(): List<Score>
//    suspend fun getAll(): LiveData<List<Score>>

    @Query("SELECT * FROM score_table ORDER BY cas ASC")
    suspend fun getAllByTime(): List<Score>

    @Query("SELECT * FROM score_table ORDER BY datum DESC")
    suspend fun getAllByDate(): List<Score>

    @Upsert
    suspend fun upsertScore(score: Score)


}