package lab.hiking_trails

import android.animation.*
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment


class SunsetFragment : Fragment() {
    private var mSceneView: View? = null
    private var mSunView: View? = null
    private var mSkyView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_sunset, container, false)
        mSceneView = view
        mSunView = view.findViewById<View>(R.id.sun)
        mSkyView = view.findViewById<View>(R.id.sky)
        mSunView!!.visibility = View.INVISIBLE
        mSceneView!!.setOnClickListener { startAnimation() }
        return view
    }

    private fun startAnimation() {
        val sunYStart = mSunView!!.top.toFloat()
        val sunYEnd = mSkyView!!.height.toFloat()
        val heightAnimator = ObjectAnimator
            .ofFloat(mSunView, "y", sunYEnd, sunYStart)
            .setDuration(1500)
        heightAnimator.interpolator = AccelerateInterpolator()
        val animatorSet = AnimatorSet()
        mSunView!!.visibility = View.VISIBLE
        animatorSet
            .play(heightAnimator)
        animatorSet.start()

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })
    }

    companion object {
        fun newInstance(): SunsetFragment {
            return SunsetFragment()
        }
    }
}