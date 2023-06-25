package com.example.pexeso2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pexeso2023.databaza.ScoreViewModel
import androidx.lifecycle.Observer

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var mScoreViewModel: ScoreViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard)

        val adapter=ScoreAdapter()
        recyclerView = findViewById(R.id.RV_score)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mScoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
        mScoreViewModel.getAll.observe(this, Observer { score -> adapter.setData(score) })

    }
}