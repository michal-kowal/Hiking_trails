package lab.hiking_trails

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Tab2Fragment : Fragment() {
    private lateinit var trails: MutableList<Trail>

    private fun isTablet(context: Context): Boolean {
        val configuration = context.resources.configuration
        return configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val trailRecycler =
            inflater.inflate(R.layout.fragment_tab2, container, false) as RecyclerView
        val dbHandler =  DBHandler(requireContext(), null, null, 1)
        trails = dbHandler.getTrailsList()
        val trailNames = trails.filter { it.localization == "Karkonosze" }
            .map { it.name }
            .toTypedArray()

        val trailImages = trails.filter { it.localization == "Karkonosze" }
            .map { it.imageId }
            .toIntArray()

        val adapter = CaptionedImagesAdapter(trailNames, trailImages, trails.filter { it.localization == "Karkonosze" }.toTypedArray())
        trailRecycler.adapter = adapter
        val isTablet = context?.let { isTablet(it) }
        if (isTablet == true){
            trailRecycler.layoutManager = GridLayoutManager(activity, 3)
        }
        else{
            trailRecycler.layoutManager = GridLayoutManager(activity, 2)
        }

        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(trail: Trail) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_TRAIL, trail)
                activity?.startActivity(intent)
            }
        })
        return trailRecycler
    }
}