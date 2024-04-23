package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class OfferEntity (
    override var id: String? = null,
    @ServerTimestamp
    override var createdAt: Date? = null,
    @ServerTimestamp
    override var deletedAt: Date? = null,
    override var collection: String = "order",


    var productId: String? = null,
    var state: String? = null,
    var type: String? = null,
): BaseEntity(id, createdAt, deletedAt, collection) {
    constructor(data: HashMap<String, Any?>) : this() {
        data.toEntity<OfferEntity>()
    }
}