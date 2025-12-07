package com.example.pushupcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import logic.PushupSensor

class CounterFragment : Fragment() {

    private var pushupCount = 0
    private var isTracking = false

    private lateinit var pushupSensor: PushupSensor

    private lateinit var pushupCountText: TextView
    private lateinit var startText: TextView
    private lateinit var startButton: ImageButton
    private lateinit var stopButton: Button

    private lateinit var resultText: TextView
    private lateinit var restartText: TextView
    private lateinit var restartButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pushupSensor = PushupSensor(requireContext())
        return inflater.inflate(R.layout.activity_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startText = view.findViewById(R.id.startText)
        pushupCountText = view.findViewById(R.id.pushupCount)
        startButton = view.findViewById(R.id.startButton)
        stopButton = view.findViewById(R.id.stopButton)

        resultText = view.findViewById(R.id.resultText)
        restartText = view.findViewById(R.id.restartText)
        restartButton = view.findViewById(R.id.restartButton)

        if (savedInstanceState != null) {
            pushupCount = savedInstanceState.getInt("PUSHUP_COUNT", 0)
            isTracking = savedInstanceState.getBoolean("IS_TRACKING", false)
            pushupCountText.text = pushupCount.toString()

            if (isTracking) {
                showTrackingUi()
            } else if (pushupCount > 0) {
                showResultUi()
            }
        }

        pushupSensor.setListener(object : PushupSensor.PushupListener {
            override fun onPushup() {
                updateCount()
            }
        })

        startButton.setOnClickListener {
            startTracking()
        }

        stopButton.setOnClickListener {
            stopTracking()
        }

        restartButton.setOnClickListener {
            resetUi()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isTracking) {
            pushupSensor.start()
        }
    }

    override fun onPause() {
        super.onPause()
        pushupSensor.stop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("PUSHUP_COUNT", pushupCount)
        outState.putBoolean("IS_TRACKING", isTracking)
    }

    private fun startTracking() {
        pushupCount = 0
        isTracking = true
        pushupCountText.text = pushupCount.toString()
        pushupSensor.start()
        showTrackingUi()
    }

    private fun stopTracking() {
        isTracking = false
        pushupSensor.stop()
        showResultUi()
    }

    private fun updateCount() {
        pushupCount++
        pushupCountText.text = pushupCount.toString()
    }

    private fun showTrackingUi() {
        pushupCountText.visibility = View.VISIBLE
        stopButton.visibility = View.VISIBLE
        startButton.visibility = View.GONE
        startText.visibility = View.GONE
        resultText.visibility = View.GONE
        restartText.visibility = View.GONE
        restartButton.visibility = View.GONE
    }

    private fun showResultUi() {
        stopButton.visibility = View.GONE
        pushupCountText.visibility = View.GONE
        resultText.text = "Ваш результат: $pushupCount раз"
        resultText.visibility = View.VISIBLE
        restartText.visibility = View.VISIBLE
        restartButton.visibility = View.VISIBLE
    }

    private fun resetUi() {
        resultText.visibility = View.GONE
        restartText.visibility = View.GONE
        restartButton.visibility = View.GONE
        startButton.visibility = View.VISIBLE
        startText.visibility = View.VISIBLE
        pushupCount = 0
        pushupCountText.text = "0"
        pushupCountText.visibility = View.GONE
    }
}