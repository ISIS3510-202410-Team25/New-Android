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

    fun readDocWithDocID(collection: String, docId: String): MutableMap<String, Any>? {
        val docRef = db.collection(collection).document(docId)
        var data: MutableMap<String, Any>? = null
        var id: String?
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    id = document.id
                    data = document.data
                    data?.set("id", id!!)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        return data
    }

    fun readAllWithDeleted(collection: String) {
        var list: Array<Any> = arrayOf()
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val id = document.id
                    val data = document.data
                    data["id"] = id
                    list += data
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
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