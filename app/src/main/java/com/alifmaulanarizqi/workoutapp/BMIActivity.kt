package com.alifmaulanarizqi.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alifmaulanarizqi.workoutapp.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var binding: ActivityBmiactivityBinding? = null

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // setup toolbar
        setSupportActionBar(binding?.tbBMI)
        // display back button on toolbar
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.tbBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        // btn calculate
        binding?.btCalculateBMI?.setOnClickListener {
            calculateBMI()
        }

        // radio btn for metric and us units
        makeVisibleMetricUnitsView()
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            }
            else {
                makeVisibleUSUnitsView()
            }
        }

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if(binding?.etMetricUnitHeight?.text.toString().isEmpty() || binding?.etMetricUnitWeight?.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true

        if(binding?.etUSUnitWeight?.text.toString().isEmpty() || binding?.etUSUnitFeet?.text.toString().isEmpty()
            || binding?.etUSUnitInch?.text.toString().isEmpty())
                isValid = false

        return isValid
    }

    private fun calculateBMI() {
        if(currentVisibleView == METRIC_UNITS_VIEW) {
            if(validateMetricUnits()) {
                val height = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weight = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmiNumber = weight / (height * height)

                displayBMIResult(bmiNumber)
            }
            else {
                Toast.makeText(this, "Please enter valid value", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            if(validateUSUnits()) {
                val weight = binding?.etUSUnitWeight?.text.toString().toFloat()
                val feet = binding?.etUSUnitFeet?.text.toString().toFloat()
                val inch = binding?.etUSUnitInch?.text.toString().toFloat()
                val height = inch + (feet * 12)

                val bmiNumber = 703 * (weight / (height * height))

                displayBMIResult(bmiNumber)
            }
            else {
                Toast.makeText(this, "Please enter valid value", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmiNumber: Float) {
        binding?.llContainerBMIInfo?.visibility = View.VISIBLE
        
        val bmiStatus: String
        val bmiDesc: String

        if(bmiNumber.compareTo(15f) <= 0) {
            bmiStatus = "Very severe underweight"
            bmiDesc = "You really need to take better car of yourself! Eat more!"
        }
        else if(bmiNumber.compareTo(15f) > 0 && bmiNumber.compareTo(16f) <= 0) {
            bmiStatus = "Severe underweight"
            bmiDesc = "You really need to take better car of yourself! Eat more!"
        }
        else if(bmiNumber.compareTo(16f) > 0 && bmiNumber.compareTo(18.5f) <= 0) {
            bmiStatus = "Underweight"
            bmiDesc = "You really need to take better car of yourself! Eat more!"
        }
        else if(bmiNumber.compareTo(18.5f) > 0 && bmiNumber.compareTo(25f) <= 0) {
            bmiStatus = "Normal"
            bmiDesc = "Congratulation! You are in good shape!"
        }
        else if(bmiNumber.compareTo(25f) > 0 && bmiNumber.compareTo(30f) <= 0) {
            bmiStatus = "Overweight"
            bmiDesc = "You really need to take better car of yourself! Workout maybe!"
        }
        else if (bmiNumber.compareTo(30f) > 0 && bmiNumber.compareTo(35f) <= 0) {
            bmiStatus = "Obese Class | (Moderately obese)"
            bmiDesc = "You really need to take care of your yourself! Workout maybe!"
        }
        else if (bmiNumber.compareTo(35f) > 0 && bmiNumber.compareTo(40f) <= 0) {
            bmiStatus = "Obese Class || (Severely obese)"
            bmiDesc = "You are in a very dangerous condition! Act now!"
        }
        else {
            bmiStatus = "Obese Class ||| (Very Severely obese)"
            bmiDesc = "You are in a very dangerous condition! Act now!"
        }

        val bmiAfterRound = BigDecimal(bmiNumber.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBMINumber?.text = bmiAfterRound
        binding?.tvBMIStatus?.text = bmiStatus
        binding?.tvBMIDesc?.text = bmiDesc

    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilContainerETMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilContainerETMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilContainerETUSUnitWeight?.visibility = View.GONE
        binding?.llContainerUSUnitHeigh?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llContainerBMIInfo?.visibility = View.GONE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.tilContainerETMetricUnitHeight?.visibility = View.GONE
        binding?.tilContainerETMetricUnitWeight?.visibility = View.GONE
        binding?.tilContainerETUSUnitWeight?.visibility = View.VISIBLE
        binding?.llContainerUSUnitHeigh?.visibility = View.VISIBLE

        binding?.etUSUnitWeight?.text!!.clear()
        binding?.etUSUnitFeet?.text!!.clear()
        binding?.etUSUnitInch?.text!!.clear()

        binding?.llContainerBMIInfo?.visibility = View.GONE
    }

}