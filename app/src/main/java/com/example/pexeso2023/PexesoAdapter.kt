package com.example.pexeso2023

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import kotlin.math.min

//private val pocetKariet: Int): RecyclerView.Adapter<PexesoAdapter.KartaViewHolder>() {
class PexesoAdapter(
    private val context: Context,
    private val plocha: Plocha,
    val obrazky: List<Int>
): RecyclerView.Adapter<PexesoAdapter.KartaViewHolder>() {
    companion object{
        const val TAG = "Pexeso Adapter"
        const val RYCHLOST: Long = 500
    }
    inner class KartaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


        private val kartaButton: ImageButton = itemView.findViewById(R.id.kartaButton)
        private lateinit var karta: Karta

        fun bind(position: Int, karta: Karta) {
            karta.obrazok=obrazky[position]

            kartaButton.setOnClickListener{
                Log.i(TAG, "poloha karty: $position")
                kartaButton.animate().apply {
                    duration= RYCHLOST
                    rotationYBy(180f)
//                    withEndAction { kartaButton.setImageResource(karta.obrazok) }
                }.start()

                Handler().postDelayed({kartaButton.setImageResource(karta.obrazok)}, RYCHLOST/2)


                //1) este nie je otocena ziadna karta -> otoc kartu
                //2) uz je jedna otocena ->
                //otoc kartu
                //skontroluj ci je to par
                //ak nie pockaj 2 sekundy a otoc ich nazad
                //3) uz su otocene 2 karty -> nedovol otocit dalsiu, toast
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KartaViewHolder {

        val velkostKarty = min(parent.height/plocha.riadky, parent.width/plocha.getStlpce()) //zatial hardocede pre easy hru

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
        holder.bind(position, Karta(false, false, position))
    }


}
