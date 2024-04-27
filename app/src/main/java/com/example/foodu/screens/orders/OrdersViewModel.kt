package com.example.foodu.screens.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodu.data.entity.OrderEntity
import com.example.foodu.data.entity.ProductEntity
import com.example.foodu.data.entity.RestaurantEntity
import com.example.foodu.repository.Filter
import com.example.foodu.repository.FirestoreInstance

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
import kotlinx.coroutines.*


@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val firestore: FirestoreInstance
) : ViewModel() {

    private val _fetchOrdersTrigger = MutableSharedFlow<Unit>()
    private val fetchOrdersTrigger = _fetchOrdersTrigger.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchOrdersTrigger.collectLatest { fetchAllOrders() }
        }
    }

    private val _currentOrders = MutableStateFlow<List<OrderDetail>>(emptyList())
    val currentOrders: StateFlow<List<OrderDetail>> = _currentOrders.asStateFlow()

    private val _historicOrders = MutableStateFlow<List<OrderDetail>>(emptyList())
    val historicOrders: StateFlow<List<OrderDetail>> = _historicOrders.asStateFlow()

    data class OrderDetail(
        val orderId: String?,
        val restaurantName: String?,
        val restaurantAddress: String?,
        val total: Double?
    )


    suspend fun fetchAllOrders() {
        val userFilter = Filter(
            "userId", "101dc99b-fc80-429c-8072-2ffa44d54367"
        )
        try {
            fetchCurrentOrders(userFilter)
            fetchHistoricalOrders(userFilter)
        } catch (e: Exception) {
            e.message?.let { Log.e("READ ERROR", it) }
        }
    }

    private suspend fun fetchCurrentOrders(userFilter: Filter) {
        runBlocking {
            try {
                val currentFilter = Filter("status", "pending")
                val currDocs = firestore.readFilters("orders", listOf(currentFilter, userFilter))
                val currEntities: MutableList<OrderEntity> = mutableListOf()
                currDocs.forEach { el ->
                    currEntities.add(OrderEntity.docToEntity(el)!!)
                }
                val cOrders: MutableList<OrderDetail> = mutableListOf()
                for (order in currEntities) {
                    val prodEntities: MutableList<ProductEntity> = mutableListOf()
                    val productList = firestore.fetchDetailsFromIdList("products", order.items)
                    productList.forEach { el -> prodEntities.add(ProductEntity.docToEntity(el)!!) }
                    var total = 0.0
                    for (prod in prodEntities) {
                        total += prod.basePrice
                    }
                    val restId = prodEntities.firstOrNull()?.restaurantId ?: ""
                    val restDoc = firestore.readDocWithDocID("restaurants", restId)
                    val restaurant = RestaurantEntity.docToEntity(restDoc!!)
                    if (restaurant != null) {
                        cOrders.add(OrderDetail(order.id, restaurant.name, restaurant.address, total))
                    }
                }

                updateCurrentOrders(cOrders)

            } catch (e: Exception) {
                Log.e("PARSE ERROR", e.stackTraceToString())
            }
        }
    }

    private fun fetchHistoricalOrders(userFilter: Filter) {
        runBlocking {
            try {
                val historyFilter = Filter("status", "delivered")
                val histDocs = firestore.readFilters("orders", listOf(historyFilter, userFilter))
                val historicalEntities: MutableList<OrderEntity> = mutableListOf()
                histDocs.forEach { el ->
                    historicalEntities.add(OrderEntity.docToEntity(el)!!)
                }
                val hOrders: MutableList<OrderDetail> = mutableListOf()
                for (order in historicalEntities) {
                    val prodEntities: MutableList<ProductEntity> = mutableListOf()
                    val productList = firestore.fetchDetailsFromIdList("products", order.items)
                    productList.forEach { el -> prodEntities.add(ProductEntity.docToEntity(el)!!) }
                    var total = 0.0
                    for (prod in prodEntities) {
                        total += prod.basePrice
                    }
                    val restId = prodEntities.firstOrNull()?.restaurantId ?: ""
                    val restDoc = firestore.readDocWithDocID("restaurants", restId)
                    val restaurant = RestaurantEntity.docToEntity(restDoc!!)
                    if (restaurant != null) {
                        hOrders.add(OrderDetail(order.id, restaurant.name, restaurant.address, total))
                    }
                }

                updateHistoricOrders(hOrders)

            } catch (e: Exception) {
                Log.e("PARSE ERROR", e.stackTraceToString())
            }
        }
    }


    private fun updateHistoricOrders(newList: List<OrderDetail>) {
        _historicOrders.value = newList
    }

    private fun updateCurrentOrders(newList: List<OrderDetail>) {
        _currentOrders.value = newList
    }

    suspend fun createData() {
        val oEntity = OrderEntity(
            id = UUID.randomUUID().toString(),
            null,
            null,
            userId = "101dc99b-fc80-429c-8072-2ffa44d54367",
            status = "pending",
            items = listOf(
                "3dfb78cd-9d89-4e8c-95ca-16ce4bc51879",
                "78a94efd-d621-4a9b-931a-5a7e19b51e43"
            )
        )

        val rEntity = RestaurantEntity(
            id = "6182bcd6-03c0-4305-a14a-1647f87261f7",
            null,
            null,
            name = "El Corral",
            rating = 5.0,
            tags = listOf(),
            spotlight = true,
            address = "Uniandes"
        )

        val pEntity = ProductEntity(
            id = "3dfb78cd-9d89-4e8c-95ca-16ce4bc51879",
            null,
            null,
            restaurantId = "6182bcd6-03c0-4305-a14a-1647f87261f7",
            name = "Corralita",
            basePrice = 20000.0,
            description = "",
            tags = listOf(),
            rating = 0.0
        )
        val pEntity2 = ProductEntity(
            id = "78a94efd-d621-4a9b-931a-5a7e19b51e43",
            null,
            null,
            restaurantId = "6182bcd6-03c0-4305-a14a-1647f87261f7",
            name = "Corralisima",
            basePrice = 25000.0,
            description = "",
            tags = listOf(),
            rating = 0.0
        )
        firestore.write(oEntity)
        firestore.write(rEntity)
        firestore.write(pEntity)
        firestore.write(pEntity2)

        viewModelScope.launch {
            _fetchOrdersTrigger.emit(Unit)
        }
    }


}