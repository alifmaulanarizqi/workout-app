package com.alifmaulanarizqi.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alifmaulanarizqi.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val statusList: ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statusItem: ExerciseModel = statusList[position]
        holder.tvItem.text = statusItem.id.toString()

        when {
            statusItem.isSelected -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_selected)
            }
            statusItem.isCompleted -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_completed)
                holder.tvItem.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white2))
            }
            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_default)
                holder.tvItem.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary_text))
            }
        }

    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    inner class ViewHolder(itemBinding: ItemExerciseStatusBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        val tvItem = itemBinding.tvItem
    }

}