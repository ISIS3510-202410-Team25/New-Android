package com.example.foodu.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.foodu.data.entity.BaseEntity
import com.google.android.gms.tasks.Task

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirestoreInstance {
    private val db = Firebase.firestore

    private fun addTimestamps(entity: HashMap<Any, Any?>): HashMap<Any, Any?> {
        entity["createdAt"] = FieldValue.serverTimestamp()
        entity["deletedAt"] = null
        return entity
    }

    suspend fun write(entity: BaseEntity): DocumentReference? {
        val id = entity.id
        val ref =  db.collection(entity.collection).document(id)
        val data = entity.toHashMap()
        ref.set(addTimestamps(data)).await()
        return ref
    }

    suspend fun readDocWithDocID(collection: String, docId: String): DocumentSnapshot? {
        return try {
            db.collection(collection).document(docId).get().await()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
            null
        }
    }

    suspend fun readAllWithDeleted(collection: String): MutableList<DocumentSnapshot> {
        val list = mutableListOf<DocumentSnapshot>()
        try {
            val res = db.collection(collection).get().await()
            for (document in res.documents) {
                list.add(document)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
        }
        return list

    }

    suspend fun readAll(collection: String): List<DocumentSnapshot> {
        val list = mutableListOf<DocumentSnapshot>()
        try {
            val result = db.collection(collection)
                .whereEqualTo("deletedAt", null)
                .get()
                .await()

            for (document in result.documents) {
                list.add(document)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
        }
        return list
    }


    suspend fun readFilters(collection: String, filters: List<Filter>): List<DocumentSnapshot> {
        val list = mutableListOf<DocumentSnapshot>()
        try {
            val colRef = db.collection(collection)

            var query: Query = colRef
            for (filter in filters) {
                query = query.whereEqualTo(filter.attrName, filter.attrVal)
            }

            val res = query.get().await()

            for (doc in res.documents) {
                list.add(doc)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
        }
        return list
    }

    suspend fun fetchDetailsFromIdList(collection: String, ids: List<String>?): MutableList<DocumentSnapshot> {
        val data = mutableListOf<DocumentSnapshot>()
        val colRef = db.collection(collection)
        ids?.forEach { id ->
            val ref = colRef.document(id)
            try {
                val doc = ref.get().await()
                if (doc.exists()) {
                    data.add(doc)
                } else {
                    Log.d("FIREBASE ERROR", "Document with ID $id not found")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting documents: ", e)
            }
        }
        return data
    }

    fun deleteWithEntity(entity: BaseEntity): Task<DocumentReference> {
        val map = entity.toHashMap()
        map["deletedAt"] = FieldValue.serverTimestamp()

        val ref = db.collection(entity.collection)
            .add(map)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        return ref
    }

}