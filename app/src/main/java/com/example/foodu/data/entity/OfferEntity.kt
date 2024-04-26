package com.example.foodu.data.entity

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class OfferEntity (
    override var id: String,
    @ServerTimestamp
    override var createdAt: Date?,
    @ServerTimestamp
    override var deletedAt: Date?,
    var productId: String,
    var state: String,
    var type: String,
    override var collection: String = "offers",
): BaseEntity(id, createdAt, deletedAt, collection)
{
    companion object {
        fun docToEntity(doc: DocumentSnapshot): OfferEntity? {
            return try {
                val id = doc.id
                val createdAt = doc.getTimestamp("createdAt")?.toDate()
                val deletedAt = doc.getTimestamp("deletedAt")?.toDate()
                val userId = doc.getString("userId") ?: throw Exception("userId is null")
                val state = doc.getString("status") ?: throw Exception("status is null")
                val type = doc.getString("type") ?: throw Exception("type is null")
                    ?: throw Exception("items is null")
                OfferEntity(id, createdAt, deletedAt, userId, state, type)
            } catch (e: Exception) {
                Log.d("ORDER ERROR", "Could not parse." + e.message)
                null
            }
        }
    }
}