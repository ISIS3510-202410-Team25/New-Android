package com.example.foodu.data.entity


import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class RestaurantEntity (
    override var id: String? = null,
    @ServerTimestamp
    override var createdAt: Date? = null,
    @ServerTimestamp
    override var deletedAt: Date? = null,
    override var collection: String = "order",


    var name: String? = null,
    var address: String? = null,
    var tags: Array<String>? = null,
    var rating: Float? = null,
    var spotlight: Boolean? = null
): BaseEntity(id, createdAt, deletedAt, collection) {
    constructor(data: HashMap<String, Any?>) : this() {
        data.toEntity<RestaurantEntity>()
    }
}