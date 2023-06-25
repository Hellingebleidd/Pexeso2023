package com.example.pexeso2023.databaza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Score::class], version =1, exportSchema = false)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object{
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        fun getDatabase(context: Context): ScoreDatabase{
            val tmpInstance = INSTANCE
            if(tmpInstance!=null){
                return tmpInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_table"
                ).build()
                INSTANCE=instance
                return instance
            }
        }

    }
}