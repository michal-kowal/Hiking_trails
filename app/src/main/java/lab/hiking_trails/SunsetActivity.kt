package lab.hiking_trails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class SunsetActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return SunsetFragment.newInstance()
    }
}