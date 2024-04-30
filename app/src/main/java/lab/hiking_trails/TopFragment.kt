package lab.hiking_trails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TopFragment : Fragment() {
    private lateinit var trails: MutableList<Trail>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val trailRecycler =
            inflater.inflate(R.layout.fragment_top, container, false) as RecyclerView
        val dbHandler =  DBHandler(requireContext(), null, null, 1)
        trails = dbHandler.getTrailsList()
        val trailNames = trails
            .map { it.name }
            .toTypedArray()

        val trailImages = trails
            .map { it.imageId }
            .toIntArray()

        val adapter = CaptionedImagesAdapter(trailNames, trailImages, trails.toTypedArray())
        trailRecycler.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        trailRecycler.layoutManager = layoutManager

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