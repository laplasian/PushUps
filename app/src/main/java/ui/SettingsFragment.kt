package com.example.pushupcounter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        val voiceModeGroup = view.findViewById<RadioGroup>(R.id.voiceModeGroup)
        val voiceOn = view.findViewById<RadioButton>(R.id.voiceOn)
        val voiceOff = view.findViewById<RadioButton>(R.id.voiceOff)
        val volumeSeekBar = view.findViewById<SeekBar>(R.id.volumeSeekBar)
        val volumeLabel = view.findViewById<TextView>(R.id.volumeLabel)
        val returnButton = view.findViewById<Button>(R.id.returnButton1)

        val isSoundOn = prefs.getBoolean("SOUND_ENABLED", true)
        val savedVolume = prefs.getInt("VOLUME_LEVEL", 50)

        if (isSoundOn) {
            voiceOn.isChecked = true
            volumeSeekBar.visibility = View.VISIBLE
            volumeLabel.visibility = View.VISIBLE
        } else {
            voiceOff.isChecked = true
            volumeSeekBar.visibility = View.GONE
            volumeLabel.visibility = View.GONE
        }
        volumeSeekBar.progress = savedVolume

        voiceModeGroup.setOnCheckedChangeListener { _, checkedId ->
            val isEnabled = (checkedId == R.id.voiceOn)

            prefs.edit().putBoolean("SOUND_ENABLED", isEnabled).apply()

            if (isEnabled) {
                volumeSeekBar.visibility = View.VISIBLE
                volumeLabel.visibility = View.VISIBLE
            } else {
                volumeSeekBar.visibility = View.GONE
                volumeLabel.visibility = View.GONE
            }
        }

        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.edit().putInt("VOLUME_LEVEL", progress).apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        returnButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}