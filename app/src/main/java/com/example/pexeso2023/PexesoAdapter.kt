package com.example.pexeso2023

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

//private val pocetKariet: Int): RecyclerView.Adapter<PexesoAdapter.KartaViewHolder>() {
class PexesoAdapter(
    private val context: Context,
    private val plocha: Plocha,
    val obrazky: List<Karta>,
    val listener: KartaClickListener
): RecyclerView.Adapter<PexesoAdapter.KartaViewHolder>() {
    companion object{
        const val TAG = "Pexeso Adapter"
    }
    inner class KartaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


        val kartaButton: ImageButton = itemView.findViewById(R.id.kartaButton)
        private lateinit var karta: Karta

        fun bind(position: Int, listener: KartaClickListener) {
            karta = obrazky[position]

            kartaButton.setOnClickListener{
                Log.d(TAG, "poloha karty: ${karta.obrazok}")
                listener.onKartaClick(position, kartaButton)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KartaViewHolder {
        val velkostKarty=min(parent.height/plocha.riadky, parent.width/plocha.getStlpce())

        val kartaL = LayoutInflater.from(context)
            .inflate(
                R.layout.karta,
                parent,
                false
            )

        val layoutKarty = kartaL.findViewById<CardView>(R.id.cardView).layoutParams
        layoutKarty.height=velkostKarty
        layoutKarty.width=velkostKarty
        return KartaViewHolder(kartaL)
    }

    override fun getItemCount() = plocha.pocetKariet

    override fun onBindViewHolder(holder: KartaViewHolder, position: Int) {
        holder.bind(position, listener)
    }


}
