package com.example.calculator.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.calculator.RnpRunner
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.repository.HistoryRepository
import kotlinx.coroutines.launch

class CalculatorViewModel(private val database: HistoryDatabase,
                          application: Application
    ) : AndroidViewModel(application) {

    private val historyRepository = HistoryRepository(database)

    private val _inputString = MutableLiveData<String>()
    val inputString: LiveData<String>
    get() = _inputString

    private val _outputString = MutableLiveData<String>()
    val outputString: LiveData<String>
        get() = _outputString

    private var id: Int = 0

    private var cleared: Boolean = true

    init {
        _inputString.value = "0"
        _outputString.value = "0.0"
        cleared = true
    }

    fun clearInput(){
        _inputString.value = "0"
        _outputString.value = "0.0"
        cleared = true
    }

    fun addSymbol(symbol: String) {
        if(cleared) {
            _inputString.value = ""
            _outputString.value = ""
        }
        if (cleared && (symbol ==  "+" || symbol ==  "-" || symbol ==  "×" || symbol ==  "÷")){
            return
        }
        if (!cleared && (symbol ==  "+" || symbol ==  "-" || symbol ==  "×" || symbol ==  "÷")) {
            if (!cleared && symbol == "+" && (_inputString.value!!.last() == '×'
                        || _inputString.value!!.last() == '÷'
                        || _inputString.value!!.last() == '+'
                        || _inputString.value!!.last() == '-')
            ) {
                return
            }
            if (!cleared && symbol == "×" && (_inputString.value!!.last() == '+'
                        || _inputString.value!!.last() == '÷'
                        || _inputString.value!!.last() == '×'
                        || _inputString.value!!.last() == '-')
            ) {
                return
            }
            if (!cleared && symbol == "÷" && (_inputString.value!!.last() == '+'
                        || _inputString.value!!.last() == '×'
                        || _inputString.value!!.last() == '÷'
                        || _inputString.value!!.last() == '-')
            ) {
                return
            }
            if (!cleared && symbol == "-" && _inputString.value!!.last() == '+') {
                _inputString.value =
                    _inputString.value!!.substring(0, _inputString.value!!.length - 1)
                _inputString.value = (_inputString.value)?.plus(symbol)
                return
            }
            if (!cleared && symbol == "-" && _inputString.value!!.last() == '-'
                && _inputString.value!!.substring(0, _inputString.value!!.length - 1).last() == '-'
            ) {
                return
            }
            if (!cleared && symbol == "-" && _inputString.value!!.last() == '-'
                && (_inputString.value!!.substring(0, _inputString.value!!.length - 1).last() == '×'
                        || _inputString.value!!.substring(0, _inputString.value!!.length - 1)
                    .last() == '÷')
            ) {
                return
            }
        }
        _inputString.value = (_inputString.value)?.plus(symbol)
        cleared = false
    }

    fun deleteLastSymbol() {
        if(_inputString.value!!.isNotEmpty()) {
            _inputString.value = (_inputString.value)?.substring(0, _inputString.value!!.length - 1)
        }
    }

    fun calculate() {
        val expRunner = RnpRunner()
        _outputString.value = expRunner.calculate(_inputString.value.toString()).toString()
        appendCalculation()

    }

    private fun appendCalculation() {
        viewModelScope.launch {
            historyRepository.addCalculation(
                id++,
                _inputString.value.toString(),
                _outputString.value.toString()
            )
        }
    }
}