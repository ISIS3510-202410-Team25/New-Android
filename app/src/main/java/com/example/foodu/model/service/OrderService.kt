package com.example.foodu.model.service

interface OrderService {
    suspend fun previousOrders(email: String, password: String)
    suspend fun currentOrders(email: String, password: String)
}