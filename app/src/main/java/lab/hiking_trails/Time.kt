package lab.hiking_trails

import android.icu.text.SimpleDateFormat

class Time (id: Int, time: Int, date: String) {
    var id: Int
    var time: Int
    var date: String

    init {
        this.id = id
        this.time = time
        this.date = date
    }
}