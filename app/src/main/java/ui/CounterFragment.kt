package com.example.pushupcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment


class CounterFragment : Fragment() {

    private var pushupCount = 0

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
        return inflater.inflate(R.layout.activity_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        startText = view.findViewById(R.id.startText)
        pushupCountText = view.findViewById(R.id.pushupCount)
        startButton = view.findViewById(R.id.startButton)
        stopButton = view.findViewById(R.id.stopButton)

        resultText = view.findViewById(R.id.resultText)
        restartText = view.findViewById(R.id.restartText)
        restartButton = view.findViewById(R.id.restartButton)

        startButton.setOnClickListener {
            pushupCount = 0
            pushupCountText.text = pushupCount.toString()
            pushupCountText.visibility = View.VISIBLE
            stopButton.visibility = View.VISIBLE
            startButton.visibility = View.GONE
            startText.visibility = View.GONE

            resultText.visibility = View.GONE
            restartText.visibility = View.GONE
            restartButton.visibility = View.GONE
        }

        stopButton.setOnClickListener {
            stopButton.visibility = View.GONE
            pushupCountText.visibility = View.GONE

            resultText.text = "Ваш результат: $pushupCount раз"
            resultText.visibility = View.VISIBLE
            restartText.visibility = View.VISIBLE
            restartButton.visibility = View.VISIBLE
        }

        restartButton.setOnClickListener {
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

    fun updateCount() {
        pushupCount++
        pushupCountText.text = pushupCount.toString()
    }
}
