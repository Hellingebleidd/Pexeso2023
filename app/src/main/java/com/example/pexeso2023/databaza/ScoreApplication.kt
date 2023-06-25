package com.example.pexeso2023.databaza

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ScoreApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { ScoreDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ScoreRepo(database.scoreDao()) }
}
