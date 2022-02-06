package com.alifmaulanarizqi.workoutapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alifmaulanarizqi.workoutapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        val historyDAO = (application as WorkoutApp).db.historyDao()
        workoutCounter(historyDAO)
        caloriesCounter(historyDAO)
        minutesCounter(historyDAO)

    }

    private fun workoutCounter(historyDAO: HistoryDAO) {
        lifecycleScope.launch {
            binding?.tvManyWorkout?.text = historyDAO.workoutCounter().toString()
        }
    }

    private fun caloriesCounter(historyDAO: HistoryDAO) {
        lifecycleScope.launch {
            if(historyDAO.calCounter() != null)
                binding?.tvManyCalories?.text = historyDAO.calCounter().toString()
            else
                binding?.tvManyCalories?.text = "0.0"
        }
    }

    private fun minutesCounter(historyDAO: HistoryDAO) {
        lifecycleScope.launch {
            if(historyDAO.timeCounter() != null)
                binding?.tvManyMinutes?.text = historyDAO.timeCounter().toString()
            else
                binding?.tvManyMinutes?.text = "0"
        }
    }

    private fun deleteRecordAlertDialog(id: Int, historyDAO: HistoryDAO) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            lifecycleScope.launch {
                historyDAO.delete(HistoryEntity(id=id))
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}