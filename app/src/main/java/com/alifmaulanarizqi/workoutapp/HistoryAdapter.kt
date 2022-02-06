package com.alifmaulanarizqi.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alifmaulanarizqi.workoutapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val historyList: ArrayList<HistoryEntity>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = historyList[position].date

        holder.tvHistoryId.text = (position + 1).toString()
        holder.tvDate.text = date
        holder.tvTimer.text = historyList[position].time
        holder.tvCalori.text = historyList[position].cal.toString()
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root) {
        val tvDate = binding.tvDate
        val tvHistoryId = binding.tvHistoryId
        val tvTimer = binding.tvTimer
        val tvCalori = binding.tvCalori
    }

}