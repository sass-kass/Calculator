package com.example.calculator.history

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.R
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.databinding.HistoryFragmentBinding

class HistoryFragment() : Fragment() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<HistoryFragmentBinding>(
            inflater, R.layout.history_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = HistoryDatabase.getInstance(application)
        val viewModelFactory = HistoryViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = HistoryAdapter()
        binding.historyListUI.adapter = adapter

        viewModel.historyList.observe(viewLifecycleOwner) { history ->
            history.let {
                adapter.submitList(history)
            }
            Log.d(TAG, "history: $history")
        }

        viewModel.getHistory()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "History"
    }
}