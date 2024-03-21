package lab.hiking_trails

class Stage (id: Int, name: String, length: Float): java.io.Serializable {
    var id: Int
    var name: String
    var length: Float

    init {
        this.id = id
        this.name = name
        this.length = length
    }
}