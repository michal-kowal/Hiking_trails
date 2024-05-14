package lab.hiking_trails

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.ContextThemeWrapper
import kotlin.properties.Delegates

class StoperFragment() : Fragment(), View.OnClickListener {
    private var seconds = 0
    private var running = false
    private var wasRunning = false
    private lateinit var trail: Trail
    private var stopTime: Long = 0

    fun setTrail(trailSent: Trail){
        trail = trailSent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
            trail = savedInstanceState.getSerializable("trail") as Trail
        }
    }
    private fun saveTimeInDb(dbHandler: DBHandler){
        dbHandler.insertStartTime(trail.id.toInt(), seconds)
        Toast.makeText(context, "Zapisano wynik w bazie. ", Toast.LENGTH_LONG).show()
    }
    private fun displayTimes(times: MutableList<Time>, savedTimesLayout: LinearLayout,
                             dbHandler: DBHandler) {
        if (times.isNotEmpty()) {
            savedTimesLayout.removeAllViews()
            val title = TextView(requireContext()).apply {
                text = "Twoje czasy:"
            }
            savedTimesLayout.addView(title)

            for (i in times.indices) {
                val date = times[i].date
                val time = times[i].time
                val hours = time / 3600
                val minutes = (time % 3600) / 60
                val secs = time % 60
                val timeConverted = String.format("%d:%02d:%02d", hours, minutes, secs)
                val textToDisplay = "${i + 1}. Czas: $timeConverted"

                val itemLayout = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }

                val textView = TextView(ContextThemeWrapper(requireContext(), R.style.TimeText),
                    null, 0).apply {
                    text = textToDisplay
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }
                itemLayout.addView(textView)

                val button1 = Button(ContextThemeWrapper(requireContext(),
                    R.style.ButtonSmallStyle), null, 0).apply {
                    text = "Wczytaj"
                    setOnClickListener {
                        seconds = times[i].time
                    }
                }
                itemLayout.addView(button1)

                val button2 = Button(ContextThemeWrapper(requireContext(),
                    R.style.ButtonSmallStyle), null, 0).apply {
                    text = "Usuń"
                    setOnClickListener {
                        dbHandler.deleteTime(times[i].id)
                        times.removeAt(i)
                        displayTimes(times, savedTimesLayout, dbHandler)
                    }
                }
                itemLayout.addView(button2)
                savedTimesLayout.addView(itemLayout)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHandler =  DBHandler(requireContext(), null, null, 1)
        val layout = inflater.inflate(R.layout.fragment_stoper, container, false)
        runStoper(layout)
        val startButton = layout.findViewById<ImageButton>(R.id.start_button)
        startButton.setOnClickListener(this)
        val stopButton = layout.findViewById<ImageButton>(R.id.stop_button)
        stopButton.setOnClickListener(this)
        val resetButton = layout.findViewById<ImageButton>(R.id.reset_button)
        resetButton.setOnClickListener(this)
        val saveButton = layout.findViewById<Button>(R.id.save_button)
        var times = dbHandler.getSavedTime(trail.id.toInt())
        val savedTimesLayout = layout.findViewById<LinearLayout>(R.id.savedTimes)
        saveButton.setOnClickListener{
            saveTimeInDb(dbHandler)
            times = dbHandler.getSavedTime(trail.id.toInt())
            displayTimes(times, savedTimesLayout, dbHandler)
        }
        displayTimes(times, savedTimesLayout, dbHandler)
        return layout
    }

    override fun onPause(){
        super.onPause()
        wasRunning = running
        running = false
    }
    override fun onStop() {
        super.onStop()
        if(running){
            wasRunning = true
        }
        stopTime = System.currentTimeMillis() / 1000
    }
    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
        if(wasRunning && stopTime > 0){
            val resumeTime = System.currentTimeMillis() / 1000
            seconds = (seconds + resumeTime - stopTime).toInt()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
        outState.putSerializable("trail", trail)
    }

    private fun onClickStart(){
        running = true
        println(trail.id)
    }

    private fun onClickStop(){
        running = false
    }

    private fun onClickReset(){
        running = false
        seconds = 0
    }

    private fun runStoper(view: View){
        val timeView = view.findViewById<TextView>(R.id.time_view)
        val handler = Handler()
        handler.post(object: Runnable {
            override fun run(){
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                val time = String.format("%d:%02d:%02d", hours, minutes, secs)
                timeView.text = time
                if(running){
                    seconds += 1
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.start_button -> onClickStart()
            R.id.stop_button -> onClickStop()
            R.id.reset_button -> onClickReset()
        }
    }
}