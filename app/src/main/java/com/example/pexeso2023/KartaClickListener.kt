package com.example.pexeso2023

import android.widget.ImageButton

interface KartaClickListener {
    fun onKartaClick(position: Int, kartaButton: ImageButton)
}