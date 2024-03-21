package lab.hiking_trails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class TrailDetailFragment : Fragment() {

    private lateinit var trail: Trail

    fun setTrail(trail: Trail){
        this.trail = trail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trail_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if(view != null){
            val title = view.findViewById<TextView>(R.id.title)
            title.text = trail.stages[0].name
        }
    }
}