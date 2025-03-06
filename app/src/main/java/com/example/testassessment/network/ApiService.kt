package com.example.testassessment.network

import com.example.testassessment.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>
}