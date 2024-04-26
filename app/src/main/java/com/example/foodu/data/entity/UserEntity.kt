package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class UserEntity(
    override var id: String,
    @ServerTimestamp
    override var createdAt: Date?,
    @ServerTimestamp
    override var deletedAt: Date?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val wishlist: List<String>,
    val offers: List<String>,
    override var collection: String = "users",
): BaseEntity(id, createdAt, deletedAt, collection)