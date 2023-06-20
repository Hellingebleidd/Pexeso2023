package com.example.pexeso2023.databaza

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "leaderboard")
data class Score(
    val obtiaznost: String,
    val cas:String,
    val datum: String
): Serializable{
    @PrimaryKey
    var uuid: UUID = UUID.randomUUID()
}
