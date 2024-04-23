package com.example.foodu.data.entity

import java.util.Date
import kotlin.reflect.KProperty1

open class BaseEntity (
    open var id: String?,
    open var createdAt: Date?,
    open var deletedAt: Date?,
    open var collection: String,
){
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

    inline fun <reified T : BaseEntity> Map<String, Any?>.toEntity(): T {
        val entity = T::class.java.newInstance()

        for ((key, value) in this) {
            val field = try {
                T::class.java.getDeclaredField(key)
            } catch (e: NoSuchFieldException) {
                continue
            }

            field.isAccessible = true
            val valueType = field.type

            if (value?.javaClass?.let { valueType.isAssignableFrom(it) } == true) {
                field.set(entity, value)
            }
        }

        return entity
    }
}