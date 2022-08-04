package ru.touchin.roboswag.core.log_file

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.core.log.databinding.LogItemBinding

class LogItemAdapter(private val context: Context, private val logItemList:MutableList<String>)
    : RecyclerView.Adapter<LogItemAdapter.LogItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogItemViewHolder {
        val binding = LogItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return LogItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogItemViewHolder, position: Int) {
        val foodItem = logItemList[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int {
        return logItemList.size
    }

    class LogItemViewHolder(logItemLayoutBinding: LogItemBinding)
        : RecyclerView.ViewHolder(logItemLayoutBinding.root){

        private val binding = logItemLayoutBinding

        fun bind(logItem: String){
            binding.logDescription.text = logItem
        }
    }
}
