package lab.hiking_trails

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TrailDetailFragment : Fragment() {

    private lateinit var trail: Trail
    private var trailId: Long = 0

    fun setTrail(trail: Trail){
        this.trail = trail
        this.trailId = trail.id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            val stoper = StoperFragment()
            val ft = childFragmentManager.beginTransaction()
            ft.add(R.id.stoper_container, stoper)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }
        else{
            trailId = savedInstanceState.getLong("trailId")
            trail = savedInstanceState.getSerializable("trail") as Trail
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trail_detail, container, false)
    }
    private fun removeTextViews(layout: LinearLayout) {
        val viewsToRemove = mutableListOf<View>()

        for (i in 0 until layout.childCount) {
            val childView = layout.getChildAt(i)

            if (childView is TextView) {
                viewsToRemove.add(childView)
            }
        }

        for (textView in viewsToRemove) {
            layout.removeView(textView)
        }
    }
    private fun addStages(stages: MutableList<Stage>, layout: LinearLayout, speed: Double,
                          fullLength: Double){
        val stagesCount = stages.size
        removeTextViews(layout)
        for(i in 1 until stagesCount){
            val currentStage = stages[i]
            val previousStage = stages[i-1]
            val time = kotlin.math.ceil(
                ((currentStage.length-previousStage.length) / speed) * 60
            )
            val textToDisplay = "$i. ${previousStage.name} - ${currentStage.name}: ${time}min"
            val textView = TextView(requireContext())
            textView.text = textToDisplay
            layout.addView(textView)
        }
        val lastStage = stages.last()
        val previousLast = stages[stagesCount - 2]
        val firstStage = stages.first()
        val textToDisplay = "$stagesCount. ${lastStage.name} - ${firstStage.name}: ${
            kotlin.math.ceil(
                ((fullLength - previousLast.length) / speed) * 60
            )
        }min"
        val textView = TextView(requireContext())
        textView.text = textToDisplay
        layout.addView(textView)
    }
    override fun onStart() {
        super.onStart()
        val view = view
        if(view != null){
            val imageView: ImageView = view.findViewById(R.id.image)
            imageView.setImageResource(trail.imageId)

            val fab: FloatingActionButton = view.findViewById(R.id.FAB)
            fab.setOnClickListener{clickFab(view)}

            val title = view.findViewById<TextView>(R.id.textName)
            title.text = trail.name
            val distance = view.findViewById<TextView>(R.id.textDistance)
            val distanceText = "Lokalizacja: " + trail.localization + ", Dystans: " + trail.length.toString() + "km"
            distance.text = distanceText
            val stagesLayout = view.findViewById<LinearLayout>(R.id.stagesLayout)
            addStages(trail.stages, stagesLayout, 5.0, trail.length.toDouble())

            val editText = view.findViewById<EditText>(R.id.editTextNumberDecimal)
            editText.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if(p0.toString() != "") {
                        val newSpeed = p0.toString().toDouble()
                        removeTextViews(stagesLayout)
                        addStages(trail.stages, stagesLayout, newSpeed, trail.length.toDouble())
                    }
                }
            })
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("trailId", trailId)
        savedInstanceState.putSerializable("trail", trail)
    }

    private fun clickFab(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(view.context.packageManager) != null) {
            startActivityForResult(takePictureIntent, 1)
        } else {
            Toast.makeText(view.context, "Brak aplikacji aparatu", Toast.LENGTH_SHORT).show()
        }
    }

}