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
        val TABLE_STAGES = "etapy"

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
    private fun getTrailStages(stageId: Int):MutableList<Stage>{
        val stagesList: MutableList<Stage> = mutableListOf()
        val query = "SELECT * FROM $TABLE_STAGES WHERE id=$stageId"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var stage: Stage?
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val length = cursor.getFloat(2)
            stage = Stage(id, name, length)
            stagesList.add(stage)
        }
        while(cursor.moveToNext()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val length = cursor.getFloat(2)
            stage = Stage(id, name, length)
            stagesList.add(stage)
        }
        cursor.close()
        return stagesList
    }

    fun getTrailsList():MutableList<Trail>{
        val trailsList: MutableList<Trail> = mutableListOf()
        val query = "SELECT * FROM $TABLE_TRAILS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var trail: Trail?
        if(cursor.moveToFirst()){
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val length = cursor.getFloat(2)
            val description = cursor.getString(3)
            val image_id = cursor.getInt(4)
            val localization = cursor.getString(5)
            val stages = getTrailStages(id.toInt())
            trail = Trail(id, name, length, description, image_id, localization, stages)
            trailsList.add(trail)
        }
        while(cursor.moveToNext()){
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val length = cursor.getFloat(2)
            val description = cursor.getString(3)
            val image_id = cursor.getInt(4)
            val localization = cursor.getString(5)
            val stages = getTrailStages(id.toInt())
            trail = Trail(id, name, length, description, image_id, localization, stages)
            trailsList.add(trail)
        }
        cursor.close()
        return trailsList
    }
}