package com.example.pexeso2023.databaza

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface LeaderboardDao {

    @Query("SELECT * FROM leaderboard")
    fun getAllScore(): List<Score>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(score: Score)

    //delete cely leaderboard??
}