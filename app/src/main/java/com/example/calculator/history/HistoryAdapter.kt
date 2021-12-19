package com.example.calculator.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import com.example.calculator.database.HistoryItem

class HistoryAdapter: ListAdapter<HistoryItem, HistoryAdapter.ViewHolder>(HistoryComparator()) {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val input: TextView = itemView.findViewById(R.id.input_text_history)
        val output: TextView = itemView.findViewById(R.id.output_text_history)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.input.text = item.inputString
        holder.output.text = item.outputString
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.history_item_view, parent, false)
        return ViewHolder(view)
    }

    class HistoryComparator: DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }
    }
}