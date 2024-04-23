package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class OrderEntity(
    override var id: String? = null,
    @ServerTimestamp
    override var createdAt: Date? = null,
    @ServerTimestamp
    override var deletedAt: Date? = null,
    override var collection: String = "order",


    var userId: String? = null,
    var status: String? = null,
    var items: Array<String>? = null,
): BaseEntity(id, createdAt, deletedAt, collection) {
    constructor(data: HashMap<String, Any?>) : this() {
        data.toEntity<OrderEntity>()
    }
}