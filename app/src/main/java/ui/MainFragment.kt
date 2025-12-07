package com.example.pushupcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val startButton: Button = view.findViewById(R.id.startButton)
        val statsButton: Button = view.findViewById(R.id.statsButton)
        val settingsButton: Button = view.findViewById(R.id.settingsButton)

        startButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_counter)
        }

        statsButton.setOnClickListener {
        }

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_settings)
        }
    }
}
