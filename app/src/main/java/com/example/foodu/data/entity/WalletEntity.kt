package com.example.foodu.data.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

class WalletEntity (
    override var id: String,
    @ServerTimestamp
    override var createdAt: Date?,
    @ServerTimestamp
    override var deletedAt: Date?,
    override var collection: String = "wallets",


    val userId: String,
    val status: String,
    val name: String,
    val brand: String,
    val cardHolder: String,
    val cardId: String,
    val expMonth: Int,
    val expYear: Int,
    val lastFour: Int,
    val default: Boolean,
): BaseEntity(id, createdAt, deletedAt, collection)