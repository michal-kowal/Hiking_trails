package lab.hiking_trails

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class TrailListFragment () : ListFragment() {
    lateinit var listener: Listener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHandler =  DBHandler(requireContext(), null, null, 1)
        val trails = dbHandler.getTrailsList()
        val names = Array(trails.size){ i ->
            trails[i].name
        }
        val adapter = ArrayAdapter<Any?>(inflater.context, R.layout.simple_list_item_1, names)
        listAdapter = adapter as ListAdapter?
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        this.listener = context as Listener
    }

    override fun onListItemClick(
        listView: ListView,
        itemView: View,
        position: Int,
        id: Long
    ) {
        listener.itemClicked(id)
    }
}