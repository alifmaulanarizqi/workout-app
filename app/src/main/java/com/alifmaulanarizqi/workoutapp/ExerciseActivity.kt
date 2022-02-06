package com.alifmaulanarizqi.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alifmaulanarizqi.workoutapp.databinding.ActivityExerciseBinding
import com.alifmaulanarizqi.workoutapp.databinding.DialogBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var exerciseStatusAdapter: ExerciseStatusAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // setup toolbar
        setSupportActionBar(binding?.tbExercise)
        // display back button on toolbar
        if(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.tbExercise?.setNavigationOnClickListener {
            backConfirmation()
        }

        // get exercise data list
        exerciseList = Constants.getExercise()

        // initialization for text-to-speech
        tts = TextToSpeech(this, this)

        // display rest view or get ready
        setRestView()

        // display exercise status recycleview
        setExerciseStatusRecycleview()

    }

    private fun setRestView() {
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.GONE
        binding?.flExerciseView?.visibility = View.GONE

        if(currentExercisePosition > -1) {
            binding?.tvNextExercise?.visibility = View.VISIBLE
            binding?.tvNextExerciseName?.visibility = View.VISIBLE

            binding?.tvTitle?.text = "REST TIME"

            speakOut("Rest for ten seconds")
        }
        else {
            try{
                val soundURI = Uri.parse("android.resource://com.alifmaulanarizqi.workoutapp/" + R.raw.press_start)

                mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
                mediaPlayer?.isLooping = false
                mediaPlayer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if(currentExercisePosition+1 <= exerciseList.size-1)
            binding?.tvNextExerciseName?.text = exerciseList[currentExercisePosition+1].name

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        startRestTimer()
    }

    private fun setExerciseView() {
        binding?.flRestView?.visibility = View.GONE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.GONE
        binding?.tvNextExerciseName?.visibility = View.GONE

        binding?.ivImage?.setImageResource(exerciseList[currentExercisePosition].image)
        binding?.tvTitle?.text = exerciseList[currentExercisePosition].name.uppercase()

        speakOut(exerciseList[currentExercisePosition].desc)

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        startExerciseTimer()
    }

    private fun setExerciseStatusRecycleview() {
        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList)
        binding?.rvExerciseStatus?.adapter = exerciseStatusAdapter
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun startRestTimer() {
        restTimer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.tvRestView?.text = (11 - restProgress).toString()
                binding?.pbRestView?.progress = 11 - restProgress
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList[currentExercisePosition].isSelected = true
                exerciseStatusAdapter.notifyDataSetChanged()
                setExerciseView()
            }
        }.start()

    }

    private fun startExerciseTimer() {
        exerciseTimer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.tvExerciseView?.text = (31 - exerciseProgress).toString()
                binding?.pbExerciseView?.progress = 31 - exerciseProgress
            }

            override fun onFinish() {
                exerciseList[currentExercisePosition].isSelected = false
                exerciseList[currentExercisePosition].isCompleted = true
                exerciseStatusAdapter.notifyDataSetChanged()

                if(currentExercisePosition < exerciseList.size-1)
                    setRestView()
                else {
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }.start()

    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun backConfirmation() {
        val dialog = Dialog(this)

        val dialogBinding = DialogBackConfirmationBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.btYes.setOnClickListener {
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }
        dialogBinding.btNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if(mediaPlayer != null)
            mediaPlayer!!.stop()
    }

    /**
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

}