package lab.hiking_trails

import androidx.fragment.app.Fragment

class SunsetActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return SunsetFragment.newInstance()
    }
}