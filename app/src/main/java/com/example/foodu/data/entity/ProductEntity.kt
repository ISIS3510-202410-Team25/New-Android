package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ProductEntity (
    override var id: String? = null,
    @ServerTimestamp
    override var createdAt: Date? = null,
    @ServerTimestamp
    override var deletedAt: Date? = null,
    override var collection: String = "products",


    var restaurantId: String? = null,
    var name: String? = null,
    var basePrice: Float? = null,
    var description: String? = null,
    var tags: List<String>? = null,
    var rating: Float? = null,

    ): BaseEntity(id, createdAt, deletedAt, collection) {
    constructor(data: HashMap<String, Any?>) : this() {
        data.toEntity<ProductEntity>()
    }
}