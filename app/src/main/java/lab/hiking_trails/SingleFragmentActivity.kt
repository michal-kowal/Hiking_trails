package lab.hiking_trails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


abstract class SingleFragmentActivity : AppCompatActivity() {
    protected abstract fun createFragment(): Fragment?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        val fm: FragmentManager = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = createFragment()
            if (fragment != null) {
                fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }
        }
    }
}