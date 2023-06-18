package com.example.pexeso2023

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class PexesoAdapter(private val context: Context, private val pocetKariet: Int): RecyclerView.Adapter<PexesoAdapter.KartaViewHolder>() {

    companion object{
        const val TAG = "Pexeso Adapter"
    }

    class KartaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val kartaButton: ImageButton = itemView.findViewById(R.id.kartaButton)
        fun bind(position: Int) {
            // todo
            kartaButton.setOnClickListener{
                Log.i(TAG, "poloha karty: $position")
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KartaViewHolder {

        val velkostKarty = min(parent.height/4, parent.width/2) //zatial hardocede pre easy hru

        val karta = LayoutInflater.from(context)
            .inflate(
                R.layout.karta,
                parent,
                false
            )

        val layoutKarty = karta.findViewById<CardView>(R.id.cardView).layoutParams
        layoutKarty.height=velkostKarty
        layoutKarty.width=velkostKarty
        return KartaViewHolder(karta)
    }

    override fun getItemCount() = pocetKariet

    override fun onBindViewHolder(holder: KartaViewHolder, position: Int) {
        holder.bind(position)
    }


}
