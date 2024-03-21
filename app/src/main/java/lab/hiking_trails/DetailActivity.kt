package lab.hiking_trails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_TRAIL = "trail"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val frag = supportFragmentManager.findFragmentById(R.id.detail_frag) as? TrailDetailFragment
        val trail = intent.extras?.getSerializable(EXTRA_TRAIL) as? Trail
        if (frag != null && trail != null) {
            frag.setTrail(trail)
        }
    }
}