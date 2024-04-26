package com.example.foodu.data.entity

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class OrderEntity(
    override var id: String,
    @ServerTimestamp override var createdAt: Date?,
    @ServerTimestamp override var deletedAt: Date?,
    var userId: String,
    var status: String,
    var items: List<String>,
    override var collection: String = "orders",
) : BaseEntity(id, createdAt, deletedAt, collection) {

    companion object {
        fun docToEntity(doc: DocumentSnapshot): OrderEntity? {
            return try {
                val id = doc.id
                val createdAt = doc.getTimestamp("createdAt")?.toDate()
                val deletedAt = doc.getTimestamp("deletedAt")?.toDate()
                val userId = doc.getString("userId") ?: throw Exception("userId is null")
                val status = doc.getString("status") ?: throw Exception("status is null")
                val items = (doc.get("items") as? List<*>)?.mapNotNull { it as String }
                    ?: throw Exception("items is null")
                OrderEntity(id, createdAt, deletedAt, userId, status, items)
            } catch (e: Exception) {
                Log.d("ORDER ERROR", "Could not parse." + e.message)
                null
            }
        }
    }
}