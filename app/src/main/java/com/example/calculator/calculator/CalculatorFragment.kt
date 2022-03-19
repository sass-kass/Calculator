package com.example.calculator.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calculator.R
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.databinding.CalculatorFragmentBinding

class CalculatorFragment() : Fragment() {

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:Bundle?): View? {
        val binding = DataBindingUtil.inflate<CalculatorFragmentBinding>(
            inflater, R.layout.calculator_fragment, container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = HistoryDatabase.getInstance(application)
        val viewModelFactory = CalculatorViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CalculatorViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.imageHistory.setOnClickListener {
            findNavController().navigate(R.id.action_calculatorFragment_to_historyFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Calculator"
    }
}