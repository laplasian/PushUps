package ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import database.AppDatabase
import java.text.SimpleDateFormat
import java.util.*
import com.example.pushupcounter.R


class StatsFragment : Fragment() {

    private lateinit var statContainer: LinearLayout
    private lateinit var menuButtonStats: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stats_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statContainer = view.findViewById(R.id.statContainer)
        menuButtonStats = view.findViewById(R.id.menuButtonStats)
        loadStats()

        menuButtonStats.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadStats() {
        val db = AppDatabase.get(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            val allStats = db.statsDao().getAll()

            val sdfMonth = SimpleDateFormat("LLLL yyyy", Locale.getDefault())
            val sdfDate = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

            val grouped = allStats.groupBy { sdfMonth.format(Date(it.date)) }

            grouped.forEach { (month, records) ->
                val monthText = TextView(requireContext()).apply {
                    text = month
                    setTextColor(Color.WHITE)
                    textSize = 24f
                    setPadding(0,16,0,8)
                }
                statContainer.addView(monthText)

                records.sortedBy { it.date }.forEach { record ->
                    val recordText = TextView(requireContext()).apply {
                        text = "${sdfDate.format(Date(record.date))} – ${record.pushups} отжиманий"
                        setTextColor(Color.GREEN)
                        textSize = 18f
                        setPadding(16,4,0,4)
                    }
                    statContainer.addView(recordText)
                }
            }
        }
    }
}
