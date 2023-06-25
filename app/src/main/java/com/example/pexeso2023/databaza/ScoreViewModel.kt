package com.example.pexeso2023.databaza


import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class ScoreViewModel(application: Application): AndroidViewModel(application) {
class ScoreViewModel(val repo: ScoreRepo): ViewModel() {
    //provide data to the UI
//
//    val getAll: LiveData<List<Score>> //aj toto by mohlo byt flow
//    private val repo: ScoreRepo
//
//    init{
//        val scoreDao = ScoreDatabase.getDatabase(application).scoreDao()
//        repo = ScoreRepo(scoreDao)
//        getAll = repo.getAll
//
//    }
//
//    fun upsertScore(score: Score){
//        viewModelScope.launch(Dispatchers.IO){
//            repo.upsertScore(score)
//        }
//    }
    val score = repo.score.asLiveData()

    class ScoreViewModelFactory(private val repo: ScoreRepo):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ScoreViewModel::class.java)){
                return ScoreViewModel(repo) as T
            }
            throw IllegalArgumentException("unknown viewModel class")
        }
    }

}