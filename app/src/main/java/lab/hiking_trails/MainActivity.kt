package lab.hiking_trails

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import lab.hiking_trails.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        copyDatabase(this)
        val dbHandler =  DBHandler(this, null, null, 1)
        val fragment = TrailsListFragment(dbHandler.getTrailsList())
        val fragTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.replace(R.id.main_fragment_container, fragment)
        fragTransaction.commit()
    }
}