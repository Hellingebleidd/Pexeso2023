package com.example.pexeso2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.example.pexeso2023.databaza.ScoreDatabase
import com.example.pexeso2023.databinding.LeaderboardBinding
import kotlinx.coroutines.launch


class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var binding: LeaderboardBinding
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: ScoreAdapter
//
//    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        if(it.resultCode== RESULT_OK){
//            val score = it.data?.getSerializableExtra("score") as Score
//            scoreViewModel.upsertScore(score)
//        }
//    }
//    val scoreViewModel: ScoreViewModel = ViewModelProvider(
//        this,
//        ScoreViewModel.ScoreViewModelFactory((application as ScoreApplication).repository)
//    ).get(ScoreViewModel::class.java)

//    val scoreViewModel: ScoreViewModel by viewModels{
//        ScoreViewModel.ScoreViewModelFactory((application as ScoreApplication).repository)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LeaderboardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        lifecycleScope.launch {
            val scoreList = ScoreDatabase(this@LeaderBoardActivity).scoreDao().getAll()
            Log.i("Leaderboard_Activity", "velkost tabulky: ${scoreList.size}")
            binding.RVScore.apply {
                layoutManager=LinearLayoutManager(this@LeaderBoardActivity)
                adapter = ScoreAdapter().apply { setData(scoreList) }
            }
        }


//        adapter=ScoreAdapter()
//        recyclerView = findViewById(R.id.RV_score)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)

//        scoreViewModel.score.observe(this){
//            adapter.zoznamScore = it
//        }
//        mScoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)
//        mScoreViewModel.getAll.observe(this, Observer { score -> adapter.setData(score) })
    }

}