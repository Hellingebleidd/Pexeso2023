package com.example.pexeso2023

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
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
//        private var otoceneKarty=0

        fun bind(position: Int, karta: Karta) {
            karta.obrazok=obrazky[position]

            kartaButton.setOnClickListener{
                Log.i(TAG, "poloha karty: ${karta.obrazok}")
                if(karta.vidnoObrazok){
                    Toast.makeText(context, "Invalid move!", Toast.LENGTH_LONG).show()
                }else {
                    karta.vidnoObrazok = true
                    kartaButton.animate().apply {
                        duration= RYCHLOST
                        rotationYBy(180f)

                    }.start()
                    kartaButton.postDelayed({kartaButton.setImageResource(karta.obrazok)}, RYCHLOST/2)
                }

//                when (otoceneKarty) {
//                    0 -> {// este nie je otocena ziadna karta
//                        otoceneKarty += 1
//                        otocKartu(karta)
//                        Log.i(TAG, "$otoceneKarty")
//                        karta.vidnoObrazok=true
//                    }
//                    1 -> { // uz je jedna otocena
//                        otoceneKarty += 1
//                        otocKartu(karta)
//                        Log.i(TAG, "$otoceneKarty")
//                        karta.vidnoObrazok=true
//                        //skontroluj ci je to par
//                        //ak nie pockaj 2 sekundy a otoc ich nazad
//                    }
//                    else -> {// uz su otocene 2 karty
//                        Log.i(TAG, "$otoceneKarty")
//                        //toast
//                        Toast.makeText(context, "Invalid move!", Toast.LENGTH_LONG).show()
//                    }
//                }
                }
            }
// PROBLEM je ze on zvysy pocet otocenych kariet len ked kliknem dvakrat na tu istu kartu netusim preco
//        private fun otocKartu(karta: Karta){
//            Log.i(TAG, "poloha karty: ${karta.obrazok}")
//            kartaButton.animate().apply {
//                duration= RYCHLOST
//                rotationYBy(180f)
//            }.start()
//            kartaButton.postDelayed({kartaButton.setImageResource(karta.obrazok)}, RYCHLOST/2)
//        }

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
