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

        operator fun invoke(context: Context) = INSTANCE?: synchronized(Any()) {
            INSTANCE ?: buildDatabase(context).also{
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            ScoreDatabase::class.java,
            "score_database"
        ).build()


//mirove
//        fun getDatabase(context: Context, scope: CoroutineScope): ScoreDatabase{
//            val tmpInstance = INSTANCE
//            if(tmpInstance!=null){
//                return tmpInstance
//            }
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ScoreDatabase::class.java,
//                    "score_table"
//                ).addCallback(ScoreDatabaseCallback(scope)).build()
//                INSTANCE=instance
//                return instance
//            }
//        }

    }
//
//    class ScoreDatabaseCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            INSTANCE?.let {
//                scope.launch {
//                    populateDatabase(it.scoreDao())
//                }
//            }
//        }
//
//        private suspend fun populateDatabase(scoreDao: ScoreDao) {
//            scoreDao.upsertScore(Score("23-6-2023", "0:16", "easy"))
//        }
//    }
}