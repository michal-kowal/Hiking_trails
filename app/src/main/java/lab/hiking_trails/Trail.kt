package lab.hiking_trails

class Trail(id: Long, name: String, length: Float, description: String, imageId: Int,
            localization: String, stages: MutableList<Stage>) : java.io.Serializable {
    var id: Long
    var name: String
    var length: Float
    var description: String
    var localization: String
    var imageId: Int
    var stages: MutableList<Stage>

    init {
        this.id = id
        this.name = name
        this.length = length
        this.description = description
        this.localization = localization
        this.imageId = imageId
        this.stages = stages
    }
}