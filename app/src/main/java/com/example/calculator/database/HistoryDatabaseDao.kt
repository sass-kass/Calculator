package com.example.calculator.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(historyItem: HistoryItem)

    @Query("SELECT * FROM calculator_history_table")
    fun getHistory(): Flow<List<HistoryItem>>

    @Query("DELETE FROM calculator_history_table")
    suspend fun deleteAll()
}