package com.example.pexeso2023

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.AsyncTask
import android.view.LayoutInflater

import android.widget.TextView
import com.example.pexeso2023.databaza.Score
import com.example.pexeso2023.databaza.ScoreDatabase
import com.example.pexeso2023.databaza.ScoreRepo
import java.util.Date
import java.util.Locale

class DialogVyhra(private val context: Context, private val yourTime: String, private val bestTime: String) {
    fun showDialog(){
        val builder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        val you = dialogView.findViewById<TextView>(R.id.yourTime_textView)
        val best = dialogView.findViewById<TextView>(R.id.bestTime_textView)
        you.text = "Your Time: $yourTime"
        best.text = "Best Time: $bestTime"
        builder.setView(dialogView)

//        builder.setTitle("You Won!")
//        builder.setMessage("Congratulations, you have won the game. please return to main menu\nYour Time: $yourTime\nBest Time: $bestTime")

        builder.setPositiveButton("OK"){dialog, _ ->
            //insertujDoDbs()
            dialog.dismiss()
            if(context is Activity) context.finish()
        }
        val dialog = builder.create()

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
    }

    fun insertujDoDbs(){
        val datum = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        var scoreDao= ScoreDatabase.getDatabase(context).scoreDao()
        val scoreRepo = ScoreRepo(scoreDao)
        val score = Score(null, datum, yourTime, "easy")
        scoreRepo.upsertScore(score)
//        AsyncTask.execute {
//            @Override
//            fun run() {
//                var score =
//                    Score(null, datum, yourTime, "easy")
//                scoreRepo.upsertScore(score)
//            }
//        }
    }
}