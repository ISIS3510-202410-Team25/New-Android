package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class WalletEntity (
    override var id: String? = null,
    @ServerTimestamp
    override var createdAt: Date? = null,
    @ServerTimestamp
    override var deletedAt: Date? = null,
    override var collection: String = "wallets",


    val userId: String? = null,
    val status: String? = null,
    val name: String? = null,
    val brand: String? = null,
    val cardHolder: String? = null,
    val cardId: String? = null,
    val expMonth: Int? = null,
    val expYear: Int? = null,
    val lastFour: Int? = null,
    val default: Boolean? = null,
): BaseEntity(id, createdAt, deletedAt, collection) {
    constructor(data: HashMap<String, Any?>) : this() {
        data.toEntity<WalletEntity>()
    }


}