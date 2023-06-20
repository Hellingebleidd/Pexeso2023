package com.example.pexeso2023.databaza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class LeaderboardDatabase : RoomDatabase(){

    abstract fun leaderboardDao(): LeaderboardDao

    companion object{
        @Volatile
        private var INSTANCE: LeaderboardDatabase? = null

        fun getDbs(context: Context): LeaderboardDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LeaderboardDatabase::class.java,
                    "leaderboard_databaza"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}