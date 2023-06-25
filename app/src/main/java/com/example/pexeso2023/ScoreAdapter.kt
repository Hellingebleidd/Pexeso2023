package com.example.pexeso2023

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pexeso2023.databaza.Score

class ScoreAdapter: RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var zoznamScore = emptyList<Score>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    class ScoreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tv_datum: TextView = itemView.findViewById(R.id.tv_datum)
        val tv_obtiaznost: TextView = itemView.findViewById(R.id.tv_obtiaznost)
        val tv_cas: TextView = itemView.findViewById(R.id.tv_cas)

        fun bind(score: Score) {
            tv_datum.text=score.datum
            tv_obtiaznost.text=score.obtiaznost
            tv_cas.text=score.cas
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
//        return ScoreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.score_riadok, parent, false))
        return LayoutInflater.from(parent.context).inflate(R.layout.score_riadok,parent,false).let { ScoreViewHolder(it) }
    }

    override fun getItemCount() = zoznamScore.size

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = zoznamScore[position]
        holder.bind(score)
    }

//    fun setData(score: List<Score>){
//        this.zoznamScore = score
//        notifyDataSetChanged()
//    }
}