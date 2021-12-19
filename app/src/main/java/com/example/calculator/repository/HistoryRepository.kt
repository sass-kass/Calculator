package com.example.calculator.repository

import com.example.calculator.database.HistoryDatabase
import com.example.calculator.database.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HistoryRepository(private val database: HistoryDatabase) {

    val history: Flow<List<HistoryItem>> = database.historyDatabaseDao.getHistory()

    suspend fun deleteHistory() {
        withContext(Dispatchers.IO) {
            database.historyDatabaseDao.deleteAll()
        }
    }

    suspend fun addCalculation(id: Int, input: String, output: String) {
        withContext(Dispatchers.IO) {
            val item = HistoryItem(id, input, output)
            database.historyDatabaseDao.insert(item)
        }
    }

    //fun getHistory(): LiveData<List<HistoryItem>> {
    //    return database.historyDatabaseDao.getHistory()
    //}

}