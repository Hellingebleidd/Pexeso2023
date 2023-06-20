package com.example.pexeso2023

import android.app.Activity
import android.app.AlertDialog
import android.content.Context

class DialogVyhra(private val context: Context, private val yourTime: String, private val bestTime: String) {
    fun showDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("You Won!")
        builder.setMessage("Congratulations, you have won the game. please return to main menu\nYour Time: $yourTime\nBest Time: $bestTime") //mozno pridat udaje best score a last score
        builder.setPositiveButton("OK"){dialog, _ ->
            dialog.dismiss()
            if(context is Activity) context.finish()
        }
        val dialog = builder.create()
        dialog.show()
    }
}