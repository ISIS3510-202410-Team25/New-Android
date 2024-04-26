package com.example.foodu.data.entity


import java.util.Date
import kotlin.reflect.KProperty1

abstract class BaseEntity (
    open var id: String,
    open var createdAt: Date?,
    open var deletedAt: Date?,
    open var collection: String,
) {
    fun toHashMap(): HashMap<Any, Any?> {
        val map = HashMap<Any, Any?>()
        val properties = this::class.members
            .filterIsInstance<KProperty1<BaseEntity, Any?>>()
            .filterNot { it.name == "collection" } // Exclude 'collection' property

        for (prop in properties) {
            map[prop.name] = prop.get(this)
        }
        return map
    }
}