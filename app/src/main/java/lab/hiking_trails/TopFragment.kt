package lab.hiking_trails

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TopFragment : Fragment() {
    private lateinit var trails: MutableList<Trail>
    private lateinit var adapter: CaptionedImagesAdapter

    private fun isTablet(context: Context): Boolean {
        val configuration = context.resources.configuration
        return configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)

        val searchEditText = view.findViewById<EditText>(R.id.search_edit_text)
        val trailRecycler = view.findViewById<RecyclerView>(R.id.top_recycler)

        val dbHandler = DBHandler(requireContext(), null, null, 1)
        trails = dbHandler.getTrailsList()
        val trailNames = trails
            .map { it.name }
            .toTypedArray()

        val trailImages = trails
            .map { it.imageId }
            .toIntArray()
        adapter = CaptionedImagesAdapter(trailNames, trailImages, trails.toTypedArray())
        trailRecycler.adapter = adapter
        val isTablet = context?.let { isTablet(it) }
        if (isTablet == true){
            trailRecycler.layoutManager = GridLayoutManager(activity, 3)
        }
        else {
            trailRecycler.layoutManager = GridLayoutManager(activity, 2)
        }

        adapter.setListener(object : CaptionedImagesAdapter.Listener {
            override fun onClick(trail: Trail) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_TRAIL, trail)
                activity?.startActivity(intent)
            }
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().toLowerCase(Locale.getDefault())
                val filteredTrails = trails.filter {
                    it.name.toLowerCase(Locale.getDefault()).contains(searchText)
                }
                adapter.setData(filteredTrails)
            }
        })

        return view
    }
}
