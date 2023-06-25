package com.example.pexeso2023.databaza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

@Database(entities = [Score::class], version =1, exportSchema = false)
//@TypeConverters(UuidConverter::class)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object{
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ScoreDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_database"
                ).addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let {
                            scope.launch{
                                it.scoreDao().upsertScore(Score(1,"23.6.2023", "0:14", "easy"))
                            }
                        }
                    }
                }).build()
                INSTANCE=instance
                instance
            }
//            val tmpInstance = INSTANCE
//            if(tmpInstance!=null){
//                return tmpInstance
//            }
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ScoreDatabase::class.java,
//                    "score_table"
//                ).build()
//                INSTANCE=instance
//                return instance
//            }
        }

    }
}

//class UuidConverter{
//    @TypeConverter
//    fun uuidToString(uuid: UUID) = uuid.toString()
//
//    @TypeConverter
//    fun stringToUuid(string: String) = UUID.fromString(string)
//
//}