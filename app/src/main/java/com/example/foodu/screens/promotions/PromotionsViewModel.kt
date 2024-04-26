package com.example.foodu.screens.promotions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodu.data.entity.OfferEntity
import com.example.foodu.data.entity.OrderEntity
import com.example.foodu.data.entity.ProductEntity
import com.example.foodu.data.entity.RestaurantEntity
import com.example.foodu.repository.Filter
import com.example.foodu.repository.FirestoreInstance
import com.example.foodu.screens.orders.OrdersViewModel

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
class PromotionsViewModel @Inject constructor(
    private val firestore: FirestoreInstance
) : ViewModel() {

    private val _fetchPromotionsTrigger = MutableSharedFlow<Unit>()
    private val fetchPromotionsTrigger = _fetchPromotionsTrigger.asSharedFlow()

    private val _historicOffers = MutableStateFlow<List<PromotionsViewModel.PromotionDetail>>(emptyList())
    val historicPromotions: StateFlow<List<PromotionsViewModel.PromotionDetail>> = _historicOffers.asStateFlow()

    init {
        viewModelScope.launch {
            fetchPromotionsTrigger.collectLatest { fetchAllPromotions() }
        }
    }

    private val _currentPromotions = MutableStateFlow<List<PromotionDetail>>(emptyList())
    val currentPromotions: StateFlow<List<PromotionDetail>> = _currentPromotions.asStateFlow()

    data class PromotionDetail(
        val promotionId: String?,
        val restaurantName: String?,
        val restaurantAddress: String?,
        val total: Double?
    )


    suspend fun fetchAllPromotions() {
        val userFilter = Filter(
            "userId", "101dc99b-fc80-429c-8072-2ffa44d54367"
        )
        try {
            fetchCurrentPromotions(userFilter)
            fetchHistoricalOffers(userFilter)
        } catch (e: Exception) {
            e.message?.let { Log.e("READ ERROR", it) }
        }
    }

    private suspend fun fetchCurrentPromotions(userFilter: Filter) {
        runBlocking {
            try {
                val currentFilter = Filter("status", "available")
                val currDocs = firestore.readFilters("offers", listOf(currentFilter, userFilter))
                val currEntities: MutableList<OfferEntity> = mutableListOf()
                currDocs.forEach { el ->
                    currEntities.add(OfferEntity.docToEntity(el)!!)
                }
                val cPromotions: MutableList<PromotionDetail> = mutableListOf()
                for (promotion in currEntities) {
                    val prodEntities: MutableList<ProductEntity> = mutableListOf()
                    var total = 0.25
                    val restId = prodEntities.firstOrNull()?.restaurantId ?: ""
                    val restDoc = firestore.readDocWithDocID("restaurants", restId)
                    val restaurant = RestaurantEntity.docToEntity(restDoc!!)
                    if (restaurant != null) {
                        cPromotions.add(PromotionDetail(promotion.id, restaurant.name, restaurant.address, total))
                    }
                }

                updateCurrentOffers(cPromotions)

            } catch (e: Exception) {
                Log.e("PARSE ERROR", e.stackTraceToString())
            }
        }
    }

    private fun fetchHistoricalOffers(userFilter: Filter) {
        runBlocking {
            try {
                val historyFilter = Filter("status", "delivered")
                val histDocs = firestore.readFilters("orders", listOf(historyFilter, userFilter))
                val historicalEntities: MutableList<OrderEntity> = mutableListOf()
                histDocs.forEach { el ->
                    historicalEntities.add(OrderEntity.docToEntity(el)!!)
                }
                val hPromotions: MutableList<PromotionDetail> = mutableListOf()
                for (promotion in historicalEntities) {
                    val prodEntities: MutableList<ProductEntity> = mutableListOf()
                    var total = 0.25
                    val restId = prodEntities.firstOrNull()?.restaurantId ?: ""
                    val restDoc = firestore.readDocWithDocID("restaurants", restId)
                    val restaurant = RestaurantEntity.docToEntity(restDoc!!)
                    if (restaurant != null) {
                        hPromotions.add(PromotionDetail(promotion.id, restaurant.name, restaurant.address, total))
                    }
                }

                //updateHistoricOffers(hPromotions)

            } catch (e: Exception) {
                Log.e("PARSE ERROR", e.stackTraceToString())
            }
        }
    }

    private fun updateCurrentOffers(newList: List<PromotionDetail>) {
        _currentPromotions.value = newList
    }

    private fun updateHistoricOffers(newList: List<PromotionsViewModel.PromotionDetail>) {
        _historicOffers.value = newList
    }

    suspend fun createData() {
        val oEntity = OfferEntity(
            id = UUID.randomUUID().toString(),
            null,
            null,
            productId = "101dc99b-fc80-429c-8072-2ffa44d54367",
            state = "Available",
            type = "Discount"
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
            id = "101dc99b-fc80-429c-8072-2ffa44d54367",
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
            _fetchPromotionsTrigger.emit(Unit)
        }
    }


}