package com.example.foodu.data.entity


import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class RestaurantEntity(
    override var id: String,
    @ServerTimestamp
    override var createdAt: Date?,
    @ServerTimestamp
    override var deletedAt: Date?,


    var name: String,
    var address: String,
    var tags: List<String>,
    var rating: Double,
    var spotlight: Boolean,
    override var collection: String = "restaurants"
) : BaseEntity(id, createdAt, deletedAt, collection) {
    companion object {
        fun docToEntity(doc: DocumentSnapshot): RestaurantEntity? {
            return try {
                val id = doc.id
                val createdAt = (doc.getTimestamp("createdAt")?.toDate())
                val deletedAt = (doc.getTimestamp("deletedAt")?.toDate())
                val name = doc.getString("name") ?: throw Exception("name is null")
                val address = doc.getString("address") ?: throw Exception("address is null")

                val tags = (doc.get("tags") as? List<*>)?.mapNotNull { it as? String }
                    ?: throw Exception("tags is null")
                val rating = doc.getDouble("rating") ?: throw Exception("rating is null")
                val spotlight = doc.getBoolean("spotlight") ?: false

                return RestaurantEntity(
                    id,
                    createdAt,
                    deletedAt,
                    name,
                    address,
                    tags,
                    rating,
                    spotlight
                )
            } catch (e: Exception) {
                Log.d("ORDER ERROR", "Could not parse." + e.message)
                null
            }
        }
    }
}