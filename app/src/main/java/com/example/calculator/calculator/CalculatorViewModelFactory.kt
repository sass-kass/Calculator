package com.example.calculator.calculator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.database.HistoryDatabaseDao
import java.lang.IllegalArgumentException

class CalculatorViewModelFactory(
    private val dataSource: HistoryDatabase,
    private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}