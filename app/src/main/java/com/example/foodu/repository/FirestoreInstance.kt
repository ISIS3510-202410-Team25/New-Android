package com.example.foodu.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.foodu.data.entity.BaseEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FirestoreInstance {
    private val db = Firebase.firestore

    private fun addTimestamps(entity: HashMap<Any, Any?>): HashMap<Any, Any?> {
        entity["createdAt"] = FieldValue.serverTimestamp()
        entity["deletedAt"] = null
        return entity
    }

    fun write(entity: BaseEntity): Task<DocumentReference> {
        val ref = db.collection(entity.collection)
            .add(addTimestamps(entity.toHashMap()))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
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

    suspend fun readFilter(collection: String, attrName: String, attrVal: String): List<DocumentSnapshot> {
        val list = mutableListOf<DocumentSnapshot>()
        try {
            val res = db.collection(collection)
                .whereEqualTo(attrName, attrVal)
                .whereEqualTo("deletedAt", null)
                .get()
                .await()

            for (doc in res.documents) {
                list.add(doc)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting documents: ", e)
        }
        return list
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