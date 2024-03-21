package lab.hiking_trails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_TRAIL_ID = "id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val frag = supportFragmentManager.findFragmentById(R.id.detail_frag) as? TrailDetailFragment
        val trailId = intent.extras?.getLong(EXTRA_TRAIL_ID)
        if (frag != null && trailId != null) {
            frag.setTrail(trailId)
        }
    }
}