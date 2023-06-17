package com.example.pexeso2023

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PexesoActivity : AppCompatActivity() {

    private lateinit var hraciaPlocha : RecyclerView
    private var karty = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pexeso)

        hraciaPlocha = findViewById(R.id.hraciaPlocha)

        val intent = intent
        karty = intent.getIntExtra("difficulty", 0)
        Log.i("HRA", "pocet parov kariet: $karty")

        val adapter = PexesoAdapter(this, karty)

        hraciaPlocha.adapter=adapter
        hraciaPlocha.setHasFixedSize(true)
        hraciaPlocha.layoutManager = GridLayoutManager(this,2) //zatial hardcoded potom podla obtiaznosti
    }
}