package com.example.pushupcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val voiceModeGroup = view.findViewById<RadioGroup>(R.id.voiceModeGroup)
        val voiceOn = view.findViewById<RadioButton>(R.id.voiceOn)
        val voiceOff = view.findViewById<RadioButton>(R.id.voiceOff)
        val volumeSeekBar = view.findViewById<SeekBar>(R.id.volumeSeekBar)
        val volumeLabel = view.findViewById<TextView>(R.id.volumeLabel)

        voiceModeGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.voiceOn) {
                volumeSeekBar.visibility = View.VISIBLE
                volumeLabel.visibility = View.VISIBLE
            } else {
                volumeSeekBar.visibility = View.GONE
                volumeLabel.visibility = View.GONE
            }
        }

        val returnButton = view.findViewById<Button>(R.id.returnButton1)
        returnButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
