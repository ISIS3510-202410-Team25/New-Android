package com.example.foodu.data.entity

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class ProductEntity(
    override var id: String,
    @ServerTimestamp override var createdAt: Date?,
    @ServerTimestamp override var deletedAt: Date?,


    var restaurantId: String,
    var name: String,
    var basePrice: Double,
    var description: String,
    var tags: List<String>,
    var rating: Double,
    override var collection: String = "products",

    ) : BaseEntity(id, createdAt, deletedAt, collection) {
    companion object {
        fun docToEntity(doc: DocumentSnapshot): ProductEntity? {
            return try {
                val id = doc.id
                val createdAt = doc.getTimestamp("createdAt")?.toDate()
                val deletedAt = doc.getTimestamp("deletedAt")?.toDate()
                val restaurantId =
                    doc.getString("restaurantId") ?: throw Exception("restaurantId is null")
                val name = doc.getString("name") ?: throw Exception("name is null")
                val basePrice = doc.getDouble("basePrice") ?: throw Exception("basePrice is null")
                val description =
                    doc.getString("description") ?: throw Exception("description is null")
                val tags = (doc.get("tags") as? List<*>)?.mapNotNull { it as? String }
                    ?: throw Exception("tags is null")
                val rating = doc.getDouble("rating") ?: throw Exception("rating is null")

                return ProductEntity(
                    id,
                    createdAt,
                    deletedAt,
                    restaurantId,
                    name,
                    basePrice,
                    description,
                    tags,
                    rating
                )
            } catch (e: Exception) {
                Log.d("ORDER ERROR", "Could not parse." + e.message)
                null
            }
        }
    }
}
