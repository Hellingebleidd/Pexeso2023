package com.example.pexeso2023.databaza

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val datum: String,
    val cas: String,
    val obtiaznost: String
) {

}