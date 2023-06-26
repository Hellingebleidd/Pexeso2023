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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

const val BUNDLE_KEY = "nacitane"
class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var binding: LeaderboardBinding
    private lateinit var buttonTime: Button
    private lateinit var buttonDate: Button
    private lateinit var scoreList: List<Score>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LeaderboardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if(savedInstanceState == null){
            nacitajData("")
        }else{
            val jsonScoreList: String? = savedInstanceState.getString(BUNDLE_KEY)
            val gson=Gson()
            val listType = object: TypeToken<List<Score>>() {}.type
            scoreList = gson.fromJson(jsonScoreList,listType)
            binding.RVScore.apply {
                layoutManager=LinearLayoutManager(this@LeaderBoardActivity)
                adapter = ScoreAdapter().apply { setData(scoreList) }
            }
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val savedList = scoreList
        val gson = Gson() //toto mi urobi Parcelable z listu aby som to vedela ulozit
        val jsonScoreList:String = gson.toJson(savedList)
        outState.putString(BUNDLE_KEY,jsonScoreList)
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

    fun orderByTime(list: List<Score>): List<Score> {
        return list.sortedBy { it.cas }
    }

    fun orderByDate(list: List<Score>): List<Score> {
        return list.sortedBy { it.datum }
    }

}