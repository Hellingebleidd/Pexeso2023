package upjs.vma.pexeso2023.databaza

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class Score(
    val datum: String,
    val cas: String,
    val obtiaznost: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int=0
}