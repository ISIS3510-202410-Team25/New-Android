package com.example.foodu.data.entity

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