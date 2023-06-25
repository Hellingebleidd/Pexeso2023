package com.example.pexeso2023.databaza

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "score_table")
data class Score(
        @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val datum: String,
    val cas: String,
    val obtiaznost: String
):Serializable {

    //    @PrimaryKey(autoGenerate = true)
//    val id: Int?=null,
//
//
//    @PrimaryKey
//    var uuid:UUID = UUID.randomUUID()
}
