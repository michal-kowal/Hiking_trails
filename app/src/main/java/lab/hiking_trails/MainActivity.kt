package lab.hiking_trails

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), Listener,
    NavigationView.OnNavigationItemSelectedListener {
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_search -> {
//                println("jajo")
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
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

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)

        val pagerAdapter =SectionsPagerAdapter(this, supportFragmentManager)
        val pager = findViewById<View>(R.id.pager) as ViewPager
        pager.adapter = pagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(pager)

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val fragment = TopFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.content_frame, fragment)
        ft.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tatry -> {
                val url = "https://pl.wikipedia.org/wiki/Tatry"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            R.id.karkonosze -> {
                val url = "https://pl.wikipedia.org/wiki/Karkonosze"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
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

    override fun onBackPressed() {
        super.onBackPressed()
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}