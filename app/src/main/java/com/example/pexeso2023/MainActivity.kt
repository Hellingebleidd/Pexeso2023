package com.example.pexeso2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnEasy: Button
    private lateinit var btnMedium: Button
    private lateinit var btnHard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEasy=findViewById(R.id.buttonEasy)
        btnMedium=findViewById(R.id.buttonMedium)
        btnHard=findViewById(R.id.buttonHard)
    }
}