package com.example.calculator.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.database.HistoryItem
import com.example.calculator.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(
    database: HistoryDatabase,
    application: Application) : AndroidViewModel(application) {

    private val historyRepository = HistoryRepository(database)

    var historyList: LiveData<List<HistoryItem>> = historyRepository.history.asLiveData()

    fun getHistory() {
        historyList = historyRepository.history.asLiveData()
    }

    fun deleteHistory() {
        viewModelScope.launch {
            historyRepository.deleteHistory()
        }
        getHistory()
    }
}