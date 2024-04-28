package lab.hiking_trails

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler (private val context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?,
version: Int): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "trails.db"

        const val TABLE_TRAILS = "trails"
        const val TABLE_STAGES = "etapy"
        const val TABLE_TIMES = "times"

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

    fun insertStartTime(trailId: Int, seconds: Int){
        val query = "INSERT INTO $TABLE_TIMES (trailId, start, saved) VALUES ($trailId, $seconds, 0)"
        val db = this.writableDatabase
        db.execSQL(query)
    }
    private fun getTrailStages(stageId: Int):MutableList<Stage>{
        val stagesList: MutableList<Stage> = mutableListOf()
        val query = "SELECT * FROM $TABLE_STAGES WHERE id_szlaku=$stageId"
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
            val image = cursor.getString(4)
            val image_id = context.resources.getIdentifier(image, "drawable", context.packageName)
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
            val image = cursor.getString(4)
            val image_id = context.resources.getIdentifier(image, "drawable", context.packageName)
            val localization = cursor.getString(5)
            val stages = getTrailStages(id.toInt())
            trail = Trail(id, name, length, description, image_id, localization, stages)
            trailsList.add(trail)
        }
        cursor.close()
        return trailsList
    }
}