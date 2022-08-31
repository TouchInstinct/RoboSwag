package ru.touchin.roboswag.loggging_reader

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.logging_reader.databinding.LogItemBinding

class LogItemAdapter(private val context: Context, private val logItemList: MutableList<String>)
    : RecyclerView.Adapter<LogItemAdapter.LogItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LogItemViewHolder(
            binding = LogItemBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: LogItemViewHolder, position: Int) {
        val logItem = logItemList[position]
        holder.bind(logItem)
    }

    override fun getItemCount(): Int {
        return logItemList.size
    }

    class LogItemViewHolder(private val binding: LogItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(logItem: String) {
            binding.logDescription.text = logItem
        }
    }
}
