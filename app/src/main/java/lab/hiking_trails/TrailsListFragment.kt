package lab.hiking_trails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lab.hiking_trails.databinding.FragmentTrailsListBinding

class TrailsListFragment (val trails: MutableList<Trail>) : Fragment() {
    private var _binding: FragmentTrailsListBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrailsListBinding.inflate(layoutInflater,container,false)

       binding.button2.setOnClickListener{
           println("guzik")
       }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}