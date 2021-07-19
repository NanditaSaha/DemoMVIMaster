package com.app.demomvimaster.data.api

import com.app.demomvimaster.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}