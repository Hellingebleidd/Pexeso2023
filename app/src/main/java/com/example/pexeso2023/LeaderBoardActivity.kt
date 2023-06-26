package com.example.pexeso2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.example.pexeso2023.databaza.Score
import com.example.pexeso2023.databaza.ScoreDatabase
import com.example.pexeso2023.databinding.LeaderboardBinding
import kotlinx.coroutines.launch


class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var binding: LeaderboardBinding
    private lateinit var buttonTime: Button
    private lateinit var buttonDate: Button
    var scoreList= emptyList<Score>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LeaderboardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        nacitajData("")
        buttonTime=findViewById(R.id.order_by_time)
        buttonDate=findViewById(R.id.order_by_date)

        buttonTime.setOnClickListener{
            val tmp=orderByTime(scoreList)
            scoreList=tmp
            nacitajData("cas")
        }

        buttonDate.setOnClickListener {
            val tmp=orderByDate(scoreList)
            scoreList=tmp
            nacitajData("datum")
        }

    }

    fun nacitajData(parameter: String){
        lifecycleScope.launch {
            when(parameter){
                "cas" ->{ scoreList = ScoreDatabase(this@LeaderBoardActivity).scoreDao().getAllByTime()}
                "datum"->{ scoreList = ScoreDatabase(this@LeaderBoardActivity).scoreDao().getAllByDate()}
                else ->  scoreList = ScoreDatabase(this@LeaderBoardActivity).scoreDao().getAll()
            }
//            scoreList = ScoreDatabase(this@LeaderBoardActivity).scoreDao().getAll()
            Log.i("Leaderboard_Activity", "velkost tabulky: ${scoreList.size}")
            binding.RVScore.apply {
                layoutManager=LinearLayoutManager(this@LeaderBoardActivity)
                adapter = ScoreAdapter().apply { setData(scoreList) }
            }
        }
    }

    fun orderByTime(list: List<Score>):List<Score>{
        val sortedList = list.sortedBy { it.cas }
        return sortedList
    }

    fun orderByDate(list: List<Score>): List<Score>{
        val sortedList = list.sortedBy { it.datum }
        return sortedList
    }

}