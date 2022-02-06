package com.alifmaulanarizqi.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.alifmaulanarizqi.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // setup toolbar
        setSupportActionBar(binding?.tbFinish)
        // display back button on toolbar
        if(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val historyDao = (application as WorkoutApp).db.historyDao()
        addWorkoutHistory(historyDao)

    }

    private fun addWorkoutHistory(historyDAO: HistoryDAO) {
        val calender = Calendar.getInstance()
        val dateTime = calender.time
        Log.e("Date", dateTime.toString())

        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

        val date = sdf.format(dateTime)
        Log.e("Formatted Date", date.toString())

        lifecycleScope.launch {
            historyDAO.insert(HistoryEntity(date=date))
        }

    }

}