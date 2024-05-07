package lab.hiking_trails

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
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
        }
    }
    private fun saveTimeInDb(dbHandler: DBHandler){
        dbHandler.insertStartTime(trail.id.toInt(), seconds)
        Toast.makeText(context, "Zapisano wynik w bazie. Możesz go potem " +
                "wczytać klikając wczytaj.", Toast.LENGTH_LONG).show()
    }

    private fun getSavedTimeFromDb(dbHandler: DBHandler){
        seconds = dbHandler.getSavedTime(trail.id.toInt())
        Toast.makeText(context, "Wczytano ostatni zapisany czas.", Toast.LENGTH_LONG).show()
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
        saveButton.setOnClickListener{
            saveTimeInDb(dbHandler)
        }
        val loadButton = layout.findViewById<Button>(R.id.load_button)
        loadButton.setOnClickListener{
            getSavedTimeFromDb(dbHandler)
        }
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