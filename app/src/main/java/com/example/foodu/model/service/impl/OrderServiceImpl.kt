package com.example.foodu.model.service.impl

import com.example.foodu.model.service.AccountService
import com.example.foodu.model.service.OrderService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderServiceImpl @Inject constructor() : OrderService {
    override suspend fun previousOrders(email: String, password: String) {
        TODO("Not yet implemented")
        //Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun currentOrders(email: String, password: String) {
        TODO("Not yet implemented")
    }
}