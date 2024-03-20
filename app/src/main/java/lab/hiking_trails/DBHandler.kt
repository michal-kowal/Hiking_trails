package lab.hiking_trails

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler (context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?,
version: Int): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "trails.db"

        val TABLE_TRAILS = "trails"

        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_LENGTH = "length"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_COLOR = "color"
        val COLUMN_IMAGE_ID = "image_id"
        val COLUMN_localization = "localization"
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
    }

    fun getTrailsList():MutableList<Trail>{
        val trailsList: MutableList<Trail> = mutableListOf()
        val query = "SELECT * FROM $TABLE_TRAILS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var trail: Trail?
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val length = cursor.getFloat(2)
            val description = cursor.getString(3)
            val color = cursor.getString(4)
            val image_id = cursor.getInt(5)
            val localization = cursor.getString(6)
            trail = Trail(id, name, length, description, color, image_id, localization)
            trailsList.add(trail)
        }
        while(cursor.moveToNext()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val length = cursor.getFloat(2)
            val description = cursor.getString(3)
            val color = cursor.getString(4)
            val image_id = cursor.getInt(5)
            val localization = cursor.getString(6)
            trail = Trail(id, name, length, description, color, image_id, localization)
            trailsList.add(trail)
        }
        cursor.close()
        return trailsList
    }
}