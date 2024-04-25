package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date
import kotlin.collections.HashMap

data class UserEntity(
    override var id: String? = null,
    @ServerTimestamp
    override var createdAt: Date? = null,
    @ServerTimestamp
    override var deletedAt: Date? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val wishlist: List<String>? = null,
    val offers: List<String>? = null,
    override var collection: String = "users",
): BaseEntity(id, createdAt, deletedAt, collection) {
    constructor(data: HashMap<String, Any?>) : this() {
        data.toEntity<UserEntity>()
    }
}