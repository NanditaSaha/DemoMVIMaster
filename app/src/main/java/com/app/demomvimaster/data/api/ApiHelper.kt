package com.app.demomvimaster.data.api

import com.app.demomvimaster.data.model.User

interface ApiHelper {
    suspend fun getUsers(): List<User>
}