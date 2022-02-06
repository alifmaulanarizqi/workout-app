package com.alifmaulanarizqi.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alifmaulanarizqi.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // setup toolbar
        setSupportActionBar(binding?.tbHistory)
        // display back button on toolbar
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Workout History"
        }
        binding?.tbHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val historyDAO = (application as WorkoutApp).db.historyDao()
        getAllHistory(historyDAO)
    }

    private fun getAllHistory(historyDAO: HistoryDAO) {
        lifecycleScope.launch {
            historyDAO.fetchAllHistory().collect {

                if(it.isNotEmpty()) {
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(applicationContext)

                    val dates = ArrayList<HistoryEntity>()
                    for(date in it) {
                        dates.add(date)
                    }

                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter
                }
                else {
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}