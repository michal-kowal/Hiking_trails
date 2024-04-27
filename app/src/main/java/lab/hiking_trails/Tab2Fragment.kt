package lab.hiking_trails

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class Tab2Fragment : ListFragment() {
    private lateinit var trails: MutableList<Trail>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val trailRecycler =
//            inflater.inflate(R.layout.fragment_tab1, container, false) as RecyclerView
        val dbHandler =  DBHandler(requireContext(), null, null, 1)
        trails = dbHandler.getTrailsList()
        val trailNames = trails.filter { it.localization == "Karkonosze" }
            .map { it.name }
            .toTypedArray()


        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,
            trailNames
        )
        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}