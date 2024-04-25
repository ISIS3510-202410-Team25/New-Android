package com.example.foodu.screens.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodu.data.entity.OrderEntity
import com.example.foodu.repository.FirestoreInstance
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val firestore: FirestoreInstance
) : ViewModel() {

    private val _fetchOrdersTrigger = MutableSharedFlow<Unit>()
    val fetchOrdersTrigger = _fetchOrdersTrigger.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchOrdersTrigger.collectLatest { fetchOrders() }
        }
    }

    private val _orders = MutableStateFlow<List<OrderEntity>>(emptyList())
    val offers: StateFlow<List<OrderEntity>> = _orders.asStateFlow()



    suspend fun fetchOrders() {
        try {
            val documents = firestore.readAll("orders")

            val entities = documents.map { document ->
                Log.d("FIRESTORE RESPONSE", document.toString())
                val id = document.id
                val createdAt = (document.getTimestamp("createdAt") ?: Timestamp.now()).toDate()
                val deletedAt = (document.getTimestamp("deletedAt") ?: Timestamp.now()).toDate()
                val userId = document.getString("userId") ?: ""
                val status = document.getString("status") ?: ""
                val items = (document.get("items") as? List<*>)?.mapNotNull { it as? String }

                OrderEntity(id, createdAt, deletedAt, userId, status, items = items)
            }
            updateOrders(entities)
        } catch (e: Exception) {
            e.message?.let { Log.e("READ ERROR", it) }
        }
    }

    private fun updateOrders(newList: List<OrderEntity>) {
        _orders.value = newList
    }

    fun createOrder() {
        val entity = OrderEntity(
            id = UUID.randomUUID().toString(),
            null,
            null,
            userId = UUID.randomUUID().toString(),
            status = "delivered",
            items = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        )
        firestore.write(entity)

        viewModelScope.launch {
            _fetchOrdersTrigger.emit(Unit)
        }
    }

}