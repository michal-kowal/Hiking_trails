package lab.hiking_trails

class Trail(id: Long, name: String, length: Float, description: String, localization: String,
            imageId: Int, color: String) {
    var id: Long
    var name: String
    var length: Float
    var description: String
    var localization: String
    var color: String
    var imageId: Int

    init {
        this.id = id
        this.name = name
        this.length = length
        this.description = description
        this.localization = localization
        this.imageId = imageId
        this.color = color
    }
}