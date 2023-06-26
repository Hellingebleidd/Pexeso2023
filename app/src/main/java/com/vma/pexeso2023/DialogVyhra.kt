package com.vma.pexeso2023

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

import android.widget.TextView
import com.example.pexeso2023.R

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
            dialog.dismiss()
            if(context is Activity) context.finish()
        }
        val dialog = builder.create()

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()
    }

}