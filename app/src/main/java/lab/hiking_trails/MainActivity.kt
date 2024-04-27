package lab.hiking_trails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), Listener {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.action_action -> {
//
//                val intent = Intent(this, DetailActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//
//    }
    private fun copyDatabase(context: Context) {
        val databaseDir = File(context.getDatabasePath("trails.db").parent)
        val files = databaseDir.listFiles()

        if (files.isNullOrEmpty()) {
            val input = context.assets.open("trails.db")
            val output = FileOutputStream(context.getDatabasePath("trails.db"))
            Toast.makeText(context, "Hello, World!", Toast.LENGTH_SHORT).show()
            input.use { input ->
                output.use { output ->
                    input.copyTo(output)
                }
            }
            val dbFile = File(context.getDatabasePath("trails.db").path)
            dbFile.setReadable(true, false)
            dbFile.setWritable(true, false)
            dbFile.setExecutable(true, false)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        copyDatabase(this)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val pagerAdapter =SectionsPagerAdapter(this, supportFragmentManager)
        val pager = findViewById<View>(R.id.pager) as ViewPager
        pager.adapter = pagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(pager)
    }

    override fun itemClicked(trail: Trail){
        val fragmentContainer: View? = findViewById(R.id.fragment_container)
        if(fragmentContainer != null){
            val details = TrailDetailFragment()
            val ft = supportFragmentManager.beginTransaction()
            details.setTrail(trail)
            ft.replace(R.id.fragment_container, details)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_TRAIL, trail)
            startActivity(intent)
        }
    }
}